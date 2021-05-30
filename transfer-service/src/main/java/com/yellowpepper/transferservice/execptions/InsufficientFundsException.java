package com.yellowpepper.transferservice.execptions;

/**
 * Exception to indicate that the account does not have enough funds to make the transfer
 */
public class InsufficientFundsException extends Throwable {
    private static final String EXCEPTION_MESSAGE = "insufficient-funds";

    public InsufficientFundsException() {
        super(EXCEPTION_MESSAGE);
    }
}
