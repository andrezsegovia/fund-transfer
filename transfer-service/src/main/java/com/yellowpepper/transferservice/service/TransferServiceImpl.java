package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.commons.DecimalFormatUtil;
import com.yellowpepper.transferservice.commons.TaxComponent;
import com.yellowpepper.transferservice.daos.TransferRepository;
import com.yellowpepper.transferservice.dtos.Transfer;
import com.yellowpepper.transferservice.execptions.ExchangeServiceException;
import com.yellowpepper.transferservice.execptions.InsufficientFundsException;
import com.yellowpepper.transferservice.pojos.Account;
import com.yellowpepper.transferservice.pojos.AccountResponse;
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

    @Override
    public Transfer doTransfer(Transfer transfer) throws ParseException {
        Account account = Account.builder().account(transfer.getOriginAccount()).build();
        if (transferExceedsAmountPerDay(account)) {
            transfer.setStatus("ERROR");
            transfer.setErrors("limit_exceeded");
            transfer.setTaxCollected(0.00f);
            return transfer;
        }
        try {
            Float taxPercentage = taxComponent.calculateTaxPercentage(transfer.getAmount());
            Float taxAmount = taxComponent.calculateTaxAmount(transfer.getAmount(), taxPercentage);
            AccountResponse accountResponse = accountAPI
                    .debit(account, (taxAmount + transfer.getAmount()));
            transfer.setStatus(accountResponse.getStatus());
            transfer.setTaxCollected(taxAmount);
            transfer.setCad(exchangeService.exchangeUSDtoCAD(taxAmount));
            return transferRepository.save(transfer);
        } catch (InsufficientFundsException | ExchangeServiceException exc) {
            transfer.setStatus("ERROR");
            transfer.setErrors(exc.getMessage());
            transfer.setTaxCollected(0.00f);
            return transferRepository.save(transfer);
        }
    }
}
