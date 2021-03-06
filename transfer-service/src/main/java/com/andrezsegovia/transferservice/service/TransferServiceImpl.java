package com.andrezsegovia.transferservice.service;

import com.andrezsegovia.transferservice.commons.DecimalFormatUtil;
import com.andrezsegovia.transferservice.commons.TaxComponent;
import com.andrezsegovia.transferservice.daos.TransferRepository;
import com.andrezsegovia.transferservice.dtos.Transfer;
import com.andrezsegovia.transferservice.execptions.AccountCreditException;
import com.andrezsegovia.transferservice.execptions.AccountDebitException;
import com.andrezsegovia.transferservice.execptions.ExchangeServiceException;
import com.andrezsegovia.transferservice.execptions.InsufficientFundsException;
import com.andrezsegovia.transferservice.pojos.Account;
import com.andrezsegovia.transferservice.pojos.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountAPI accountAPI;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private TaxComponent taxComponent;

    @Override
    public Transfer persistTransfer(Transfer transfer) {
        return this.transferRepository.save(transfer);
    }

    @Override
    public Transfer findTransferById(int id) {
        return this.transferRepository.getById(id);
    }


    boolean transferExceedsAmountPerDay(Account account) throws ParseException {
        final int MAX_AMOUNT_TRANSFERS_PER_DAY = 3;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(new Date());
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS");
        Date startDate = simpleDateFormat.parse(currentDate + " 00:00:00.000");
        Date endDate = simpleDateFormat.parse(currentDate + " 23:59:59.999");

        Integer transferTotal = transferRepository
                .totalTransfersByCreationDateRange(account.getAccount(), startDate, endDate);

        if (transferTotal >= MAX_AMOUNT_TRANSFERS_PER_DAY) {
            return true;
        }
        return false;
    }

    // TODO implements transactionality
    @Override
    public Transfer doTransfer(Transfer transfer) throws ParseException {
        Account accountOrigin = Account.builder().account(transfer.getOriginAccount()).build();
        Account accountDestination = Account.builder().account(transfer.getDestinationAccount()).build();
        if (transferExceedsAmountPerDay(accountOrigin)) {
            transfer.setStatus("ERROR");
            transfer.setErrors("limit_exceeded");
            transfer.setTaxCollected(0.00f);
            return transfer;
        }
        try {
            Float taxPercentage = taxComponent.calculateTaxPercentage(transfer.getAmount());
            Float taxAmount = taxComponent.calculateTaxAmount(transfer.getAmount(), taxPercentage);
            accountAPI.debit(accountOrigin, (taxAmount + transfer.getAmount()));
            accountAPI.credit(accountDestination, transfer.getAmount());
            transfer.setStatus("OK");
            transfer.setTaxCollected(taxAmount);
            transfer.setCad(exchangeService.exchangeUSDtoCAD(taxAmount));
            return transferRepository.save(transfer);
        } catch (AccountDebitException | ExchangeServiceException | AccountCreditException exc) {
            transfer.setStatus("ERROR");
            transfer.setErrors(exc.getMessage());
            transfer.setTaxCollected(0.00f);
            return transferRepository.save(transfer);
        }
    }
}
