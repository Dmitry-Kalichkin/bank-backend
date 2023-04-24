package ru.bank.accounts.data.exception;

public class OperationCantBeCompletedException extends RuntimeException {
    public OperationCantBeCompletedException(String message) {
        super(message);
    }
}
