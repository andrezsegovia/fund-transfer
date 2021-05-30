package com.yellowpepper.accountservice.exceptions;

/**
 * Exception to indicate that the account does not have enough funds to make the transfer
 */
public class InsufficientFundsException extends Throwable {
    private static final String INSUFFICIENT_FUNDS = "insufficient-funds";

    public InsufficientFundsException() {
        super(INSUFFICIENT_FUNDS);
    }
}
