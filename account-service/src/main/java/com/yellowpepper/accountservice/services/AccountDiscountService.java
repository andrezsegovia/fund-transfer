package com.yellowpepper.accountservice.services;

import com.yellowpepper.accountservice.dtos.Account;
import com.yellowpepper.accountservice.pojos.AccountDiscountRequest;

public interface AccountDiscountService {

    Account discount(AccountDiscountRequest accountDiscountRequest);
}
