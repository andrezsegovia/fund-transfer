package com.yellowpepper.accountservice.services;

import com.yellowpepper.accountservice.daos.AccountRepository;
import com.yellowpepper.accountservice.dtos.Account;
import com.yellowpepper.accountservice.pojos.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AccountService {

    Account findAccountByAccountNumber(String accountNumber);

    Account createAccount(Account account);
}
