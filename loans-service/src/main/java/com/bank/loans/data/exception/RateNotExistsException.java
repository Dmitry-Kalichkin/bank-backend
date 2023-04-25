package com.bank.loans.data.exception;

public class RateNotExistsException extends RuntimeException {
    public RateNotExistsException(String message) {
        super(message);
    }
}
