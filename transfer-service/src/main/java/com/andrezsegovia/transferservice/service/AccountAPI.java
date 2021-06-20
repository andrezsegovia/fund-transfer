package com.andrezsegovia.transferservice.service;

import com.andrezsegovia.transferservice.execptions.AccountCreditException;
import com.andrezsegovia.transferservice.execptions.AccountDebitException;
import com.andrezsegovia.transferservice.execptions.InsufficientFundsException;
import com.andrezsegovia.transferservice.pojos.Account;
import com.andrezsegovia.transferservice.pojos.AccountResponse;

public interface AccountAPI {

    Account findAccountByNumber(Integer accountNumber);

    AccountResponse credit(Account account, Float amount) throws AccountCreditException;

    AccountResponse debit(Account account, Float amount) throws AccountDebitException;

}
