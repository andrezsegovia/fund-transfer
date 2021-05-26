package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.pojos.Account;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AccountAPIImpl implements AccountAPI {

    @Override
    public Account findAccountByNumber(Integer accountNumber) {

        return null;
    }
}
