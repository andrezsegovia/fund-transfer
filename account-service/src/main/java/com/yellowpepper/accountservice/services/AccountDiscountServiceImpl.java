package com.yellowpepper.accountservice.services;

import com.yellowpepper.accountservice.daos.AccountRepository;
import com.yellowpepper.accountservice.dtos.Account;
import com.yellowpepper.accountservice.pojos.AccountDiscountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDiscountServiceImpl implements AccountDiscountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account discount(AccountDiscountRequest accountDiscountRequest) {
        Account account = accountRepository.findAccountByAccount(accountDiscountRequest.getAccount());
        Float balance = account.getAccountBalance() - accountDiscountRequest.getDiscount();
        account.setAccountBalance( balance );
        accountRepository.updateAccountBalanceByAccount(account.getAccount(), balance);
        return account;
    }
}
