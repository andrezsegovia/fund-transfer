package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.execptions.AccountCreditException;
import com.yellowpepper.transferservice.execptions.AccountDebitException;
import com.yellowpepper.transferservice.execptions.InsufficientFundsException;
import com.yellowpepper.transferservice.pojos.Account;
import com.yellowpepper.transferservice.pojos.AccountResponse;

public interface AccountAPI {

    Account findAccountByNumber(Integer accountNumber);

    AccountResponse credit(Account account, Float amount) throws AccountCreditException;

    AccountResponse debit(Account account, Float amount) throws AccountDebitException;

}
