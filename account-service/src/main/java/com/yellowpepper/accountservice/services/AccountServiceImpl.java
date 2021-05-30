package com.yellowpepper.accountservice.services;

import com.yellowpepper.accountservice.daos.AccountRepository;
import com.yellowpepper.accountservice.dtos.Account;
import com.yellowpepper.accountservice.pojos.AccountUpdateBalanceRequest;
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
    public Account updateAccountBalance(AccountUpdateBalanceRequest accountUpdateBalanceRequest) {
        accountRepository.updateAccountBalanceByAccount(accountUpdateBalanceRequest.getAccount(),
                accountUpdateBalanceRequest.getBalance());
        return accountRepository.findAccountByAccount(accountUpdateBalanceRequest.getAccount());
    }
}
