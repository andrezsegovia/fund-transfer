package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.daos.TransferRepository;
import com.yellowpepper.transferservice.dtos.Transfer;
import com.yellowpepper.transferservice.execptions.InsufficientFundsException;
import com.yellowpepper.transferservice.pojos.Account;
import com.yellowpepper.transferservice.pojos.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountAPI accountAPI;

    @Override
    public Transfer persistTransfer(Transfer transfer) {
        return this.transferRepository.save(transfer);
    }

    @Override
    public Transfer findTransferById(int id) {
        return this.transferRepository.getById(id);
    }

    private Float calculateTaxAmount(Float transferAmount, Float percentage) {
        System.out.println("Transfer Tax: " + (transferAmount * percentage));
        return transferAmount * percentage;
    }

    private Float calculateTaxPercentage(Float transferAmount) {
        System.out.println("Transfer Amount: " + transferAmount);
        if(transferAmount >= 100.0f) {
            return 0.005f;
        }
        return 0.002f;
    }

    private float convertUSDtoCADCurrency(Float usd) {
        //TODO unimplemented functionality
        return usd * 1.21f;
    }

    @Override
    public Transfer doTransfer(Transfer transfer) {
        /**
         * 1. Retrieve the current fund of the account
         * 2. Discount the fund amount of the transfer to the current fund
         * 3. Discount the tax to the current fund
         * 4. Validate whether the result of the discount is positive or not.
         * 5. Update the account fund with the new fund
         * 6. Store the transfer attend on DB
         * 7. Return the transfer information
         */
        try {
            Float taxPercentage = calculateTaxPercentage(transfer.getAmount());
            Float taxAmount = calculateTaxAmount(transfer.getAmount(), taxPercentage);
            Account account = Account.builder().account(transfer.getOriginAccount()).build();
            AccountResponse accountResponse = accountAPI
                    .discountAmount(account, transfer.getAmount() + taxAmount);
            transfer.setStatus(accountResponse.getStatus());
            transfer.setTaxCollected(taxAmount);
            transfer.setCad(convertUSDtoCADCurrency(taxAmount));
            return transferRepository.save(transfer);
        }catch (InsufficientFundsException exc) {
            transfer.setStatus("ERROR");
            transfer.setErrors(exc.getMessage());
            transfer.setTaxCollected(0.00f);
            return transferRepository.save(transfer);
        }
    }
}
