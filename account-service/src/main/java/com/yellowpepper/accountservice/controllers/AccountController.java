package com.yellowpepper.accountservice.controllers;

import com.yellowpepper.accountservice.dtos.Account;
import com.yellowpepper.accountservice.exceptions.InsufficientFundsException;
import com.yellowpepper.accountservice.mappers.AccountMapper;
import com.yellowpepper.accountservice.pojos.AccountUpdateBalanceRequest;
import com.yellowpepper.accountservice.pojos.AccountRequest;
import com.yellowpepper.accountservice.pojos.AccountResponse;
import com.yellowpepper.accountservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @RequestMapping(value = "/find", method =  {RequestMethod.POST})
    public ResponseEntity<AccountResponse> getAccount(@RequestBody AccountRequest accountRequest) {
        Account account = accountService.findAccountByAccountNumber(accountRequest.getAccount());
        AccountResponse accountResponse = AccountResponse.builder().status("OK")
                .accountBalance(account.getAccountBalance()).errors(new String[]{}).build();
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<AccountResponse> create(@RequestBody AccountRequest accountRequest) {
        accountService.createAccount(accountMapper.accountRequestToAccount(accountRequest));
        AccountResponse accountResponse = AccountResponse.builder().status("OK").errors(new String[]{}).build();
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    @RequestMapping(value =  "/debit", method = RequestMethod.POST)
    public ResponseEntity<AccountResponse> discount(@RequestBody AccountUpdateBalanceRequest accountDiscountRequest) {
        try {
            Account account = accountService.debit(accountDiscountRequest);
            AccountResponse accountResponse = AccountResponse.builder().status("OK")
                    .accountBalance(account.getAccountBalance()).errors(new String[]{}).build();
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (InsufficientFundsException e) {
            // TODO the API must use the corresponding Http Status Codes to indicate errors rather than always 200
            return new ResponseEntity<>(AccountResponse.builder().status("ERROR")
                    .errors(new String[]{e.getMessage()}).build(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/credit", method = RequestMethod.POST)
    public ResponseEntity<AccountResponse> credit(@RequestBody AccountUpdateBalanceRequest accountUpdateBalanceRequest) {
        Account account = accountService.credit(accountUpdateBalanceRequest);
        AccountResponse accountResponse = AccountResponse.builder().status("OK")
                .accountBalance(account.getAccountBalance()).errors(new String[]{}).build();
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
}
