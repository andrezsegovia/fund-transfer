package com.andrezsegovia.accountservice.services;

import com.andrezsegovia.accountservice.daos.AccountRepository;
import com.andrezsegovia.accountservice.dtos.Account;
import com.andrezsegovia.accountservice.exceptions.InsufficientFundsException;
import com.andrezsegovia.accountservice.pojos.AccountRequest;
import com.andrezsegovia.accountservice.pojos.AccountUpdateBalanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AccountService {

    Account findAccountByAccountNumber(String accountNumber);

    Account createAccount(Account account);

    Account credit(AccountUpdateBalanceRequest accountUpdateBalanceRequest);

    Account debit(AccountUpdateBalanceRequest accountUpdateBalanceRequest) throws InsufficientFundsException;
}
