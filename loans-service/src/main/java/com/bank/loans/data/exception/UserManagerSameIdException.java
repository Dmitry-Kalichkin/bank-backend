package com.bank.loans.data.exception;

import lombok.Getter;
import lombok.Setter;

public class UserManagerSameIdException extends RuntimeException {
    @Getter @Setter
    private String logMessage;
    public UserManagerSameIdException(String message, String logMessage) {
        super(message);
        this.logMessage = logMessage;
    }
}
