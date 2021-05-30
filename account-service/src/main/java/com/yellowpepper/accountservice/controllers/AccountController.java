package com.yellowpepper.accountservice.controllers;

import com.yellowpepper.accountservice.dtos.Account;
import com.yellowpepper.accountservice.mappers.AccountMapper;
import com.yellowpepper.accountservice.pojos.AccountRequest;
import com.yellowpepper.accountservice.pojos.AccountResponse;
import com.yellowpepper.accountservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @RequestMapping(value = "/account/find", method =  {RequestMethod.POST})
    public ResponseEntity<AccountResponse> getAccount(@RequestBody AccountRequest accountRequest) {
        Account account = accountService.findAccountByAccountNumber(accountRequest.getAccount());
        AccountResponse accountResponse = AccountResponse.builder().status("OK")
                .accountBalance(account.getAccountBalance()).build();
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public ResponseEntity<AccountResponse> create(@RequestBody AccountRequest accountRequest) {
        Account account = accountService
                .createAccount(accountMapper.accountRequestToAccount(accountRequest));
        AccountResponse accountResponse = AccountResponse.builder().status("OK").build();
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
}
