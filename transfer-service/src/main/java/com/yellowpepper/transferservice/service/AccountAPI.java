package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.execptions.InsufficientFundsException;
import com.yellowpepper.transferservice.pojos.Account;
import com.yellowpepper.transferservice.pojos.AccountResponse;

public interface AccountAPI {

    Account findAccountByNumber(Integer accountNumber);

    AccountResponse credit(Account account, Float amount) throws InsufficientFundsException;

    AccountResponse debit(Account account, Float amount) throws InsufficientFundsException;

}
