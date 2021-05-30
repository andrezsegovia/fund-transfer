package com.yellowpepper.accountservice.services;

import com.yellowpepper.accountservice.daos.AccountRepository;
import com.yellowpepper.accountservice.dtos.Account;
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
}
