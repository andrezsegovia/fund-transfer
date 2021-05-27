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
            Float taxAmount = 0f;
            if(transfer.getAmount() > 1000) {
                taxAmount = transfer.getAmount() * 0.5f;
            } else {
                taxAmount = transfer.getAmount() * 0.2f;
            }
            Account account = Account.builder().account(transfer.getOriginAccount()).build();
            accountAPI.discountAmount(account, transfer.getAmount()+taxAmount);
            transfer.setStatus("OK");
            transfer.setTaxCollected(taxAmount);
            transfer.setCad(66.928861615);
            return transferRepository.save(transfer);
        }catch (InsufficientFundsException exc) {
            transfer.setStatus("Error");
            transfer.setErrors(exc.getMessage());
            return transferRepository.save(transfer);
        }
    }
}
