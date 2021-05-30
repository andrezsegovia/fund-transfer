package com.yellowpepper.transferservice.execptions;

public class AccountCreditException extends Throwable{
    private static final String EXCEPTION_MESSAGE = "account-credit-error";

    public AccountCreditException() {
        super(EXCEPTION_MESSAGE);
    }

    public AccountCreditException(String message) {
        super(message);
    }
}
