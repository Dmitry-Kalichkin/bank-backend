package com.bank.loans.data.exception;

public class RateNameExistsException extends RuntimeException {
    public RateNameExistsException(String message) {
        super(message);
    }
}
