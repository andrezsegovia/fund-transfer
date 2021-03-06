package com.andrezsegovia.accountservice.services;

import com.andrezsegovia.accountservice.daos.AccountRepository;
import com.andrezsegovia.accountservice.dtos.Account;
import com.andrezsegovia.accountservice.exceptions.InsufficientFundsException;
import com.andrezsegovia.accountservice.pojos.AccountUpdateBalanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccount(accountNumber);
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account credit(AccountUpdateBalanceRequest accountUpdateBalanceRequest) {
        Account account = accountRepository.findAccountByAccount(accountUpdateBalanceRequest.getAccount());
        account.setAccountBalance(account.getAccountBalance() + accountUpdateBalanceRequest.getAmount());
        return accountRepository.save(account);
    }

    @Override
    public Account debit(AccountUpdateBalanceRequest accountUpdateBalanceRequest) throws InsufficientFundsException {
        Account account = accountRepository.findAccountByAccount(accountUpdateBalanceRequest.getAccount());
        Float newAccountBalance = account.getAccountBalance() - accountUpdateBalanceRequest.getAmount();
        if(newAccountBalance < 0) {
            throw new InsufficientFundsException();
        }
        account.setAccountBalance(newAccountBalance);
        return accountRepository.save(account);
    }
}
