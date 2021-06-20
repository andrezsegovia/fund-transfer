package com.andrezsegovia.transferservice.execptions;

public class AccountDebitException extends Throwable {
    private static final String EXCEPTION_MESSAGE = "account-debit-error";

    public AccountDebitException() {
        super(EXCEPTION_MESSAGE);

    }
    public AccountDebitException(String message) {
        super(message);
    }
}
