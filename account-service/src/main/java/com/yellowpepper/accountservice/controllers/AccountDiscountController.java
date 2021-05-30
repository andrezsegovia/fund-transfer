package com.yellowpepper.accountservice.controllers;

import com.yellowpepper.accountservice.dtos.Account;
import com.yellowpepper.accountservice.pojos.AccountDiscountRequest;
import com.yellowpepper.accountservice.pojos.AccountRequest;
import com.yellowpepper.accountservice.pojos.AccountResponse;
import com.yellowpepper.accountservice.services.AccountDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountDiscountController {

    @Autowired
    private AccountDiscountService accountDiscountService;

    @RequestMapping(value =  "/balance", method = RequestMethod.POST)
    public ResponseEntity<AccountResponse> discount(@RequestBody AccountDiscountRequest accountDiscountRequest) {
        Account account = accountDiscountService.discount(accountDiscountRequest);
        AccountResponse accountResponse = AccountResponse.builder().status("OK").accountBalance(account.getAccountBalance()).build();
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
}
