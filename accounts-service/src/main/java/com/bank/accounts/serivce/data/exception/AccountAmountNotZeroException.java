package ru.bank.accounts.data.exception;

public class AccountAmountNotZeroException extends RuntimeException {
    public AccountAmountNotZeroException(String message) {
        super(message);
    }
}
