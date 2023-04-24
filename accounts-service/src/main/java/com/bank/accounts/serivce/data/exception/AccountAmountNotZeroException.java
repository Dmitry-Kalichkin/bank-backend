package com.bank.accounts.serivce.data.exception;

public class AccountAmountNotZeroException extends RuntimeException {
    public AccountAmountNotZeroException(String message) {
        super(message);
    }
}
