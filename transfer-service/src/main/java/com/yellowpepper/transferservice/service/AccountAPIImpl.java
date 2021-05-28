package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.componets.ClientAPI;
import com.yellowpepper.transferservice.execptions.InsufficientFundsException;
import com.yellowpepper.transferservice.pojos.Account;
import com.yellowpepper.transferservice.pojos.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountAPIImpl implements AccountAPI {

    @Autowired
    private ClientAPI clientAPI;

    @Override
    public Account findAccountByNumber(Integer accountNumber) {
        //TODO optimize hard code
        AccountResponse accountResponse = clientAPI.get("http://localhost:8082/", AccountResponse.class);
        return accountResponse;
    }

    @Override
    public void discountAmount(Account account, Float amount) throws InsufficientFundsException {
        Map<Object, Object> requestBody = new HashMap<>();
        requestBody.put("account", account.getAccount());
        requestBody.put("amount", amount);
        //TODO optimize hard code
        AccountResponse accountResponse = clientAPI
                .post("http://localhost:8082/", requestBody, AccountResponse.class);
        if (!accountResponse.getStatus().equals("OK") || accountResponse.getErrors().length > 0) {
            throw new InsufficientFundsException();
        }
    }
}
