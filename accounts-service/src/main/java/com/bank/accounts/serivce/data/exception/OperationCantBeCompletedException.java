package com.bank.accounts.serivce.data.exception;

public class OperationCantBeCompletedException extends RuntimeException {
    public OperationCantBeCompletedException(String message) {
        super(message);
    }
}
