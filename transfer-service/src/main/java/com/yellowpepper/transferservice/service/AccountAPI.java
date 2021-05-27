package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.execptions.InsufficientFundsException;
import com.yellowpepper.transferservice.pojos.Account;

public interface AccountAPI {

    Account findAccountByNumber(Integer accountNumber);

    void discountAmount(Account account, Float amount) throws InsufficientFundsException;

}
