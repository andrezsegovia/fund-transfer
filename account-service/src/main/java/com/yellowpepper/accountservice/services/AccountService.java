package com.yellowpepper.accountservice.services;

import com.yellowpepper.accountservice.daos.AccountRepository;
import com.yellowpepper.accountservice.dtos.Account;
import com.yellowpepper.accountservice.exceptions.InsufficientFundsException;
import com.yellowpepper.accountservice.pojos.AccountRequest;
import com.yellowpepper.accountservice.pojos.AccountUpdateBalanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AccountService {

    Account findAccountByAccountNumber(String accountNumber);

    Account createAccount(Account account);

    Account credit(AccountUpdateBalanceRequest accountUpdateBalanceRequest);

    Account debit(AccountUpdateBalanceRequest accountUpdateBalanceRequest) throws InsufficientFundsException;
}
