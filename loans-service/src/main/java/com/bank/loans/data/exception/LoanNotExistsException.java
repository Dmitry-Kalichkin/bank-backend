package com.bank.loans.data.exception;

public class LoanNotExistsException extends RuntimeException {
    public LoanNotExistsException(String message) {
        super(message);
    }
}
