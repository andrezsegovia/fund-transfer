package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.pojos.Account;

public interface AccountAPI {

    Account findAccountByNumber(Integer accountNumber);
}
