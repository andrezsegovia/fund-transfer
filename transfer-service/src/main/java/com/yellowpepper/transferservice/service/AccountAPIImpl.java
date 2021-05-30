package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.componets.ClientAPI;
import com.yellowpepper.transferservice.execptions.AccountCreditException;
import com.yellowpepper.transferservice.execptions.AccountDebitException;
import com.yellowpepper.transferservice.execptions.InsufficientFundsException;
import com.yellowpepper.transferservice.pojos.Account;
import com.yellowpepper.transferservice.pojos.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountAPIImpl implements AccountAPI {

    @Value("${local.account.service.url}")
    private String ACCOUNT_API_URL;

    @Autowired
    private ClientAPI clientAPI;

    @Override
    public Account findAccountByNumber(Integer accountNumber) {
        AccountResponse accountResponse = clientAPI.get(ACCOUNT_API_URL, AccountResponse.class);
        return accountResponse;
    }

    @Override
    public AccountResponse credit(Account account, Float amount) throws AccountCreditException {
        Map<Object, Object> requestBody = new HashMap<>();
        requestBody.put("account", account.getAccount());
        requestBody.put("amount", amount);
        AccountResponse accountResponse = clientAPI
                .post(ACCOUNT_API_URL+"/account/credit", requestBody, AccountResponse.class);
        if (accountResponse.getStatus().equals("ERROR") || accountResponse.getErrors().length > 0) {
            throw new AccountCreditException();
        }
        return accountResponse;
    }

    @Override
    public AccountResponse debit(Account account, Float amount) throws AccountDebitException {
        Map<Object, Object> requestBody = new HashMap<>();
        requestBody.put("account", account.getAccount());
        requestBody.put("amount", amount);
        AccountResponse accountResponse = clientAPI
                .post(ACCOUNT_API_URL+"/account/debit", requestBody, AccountResponse.class);
        if (accountResponse.getStatus().equals("ERROR") || accountResponse.getErrors().length > 0) {
            throw new AccountDebitException(String.join(",", accountResponse.getErrors()));
        }
        return accountResponse;
    }
}
