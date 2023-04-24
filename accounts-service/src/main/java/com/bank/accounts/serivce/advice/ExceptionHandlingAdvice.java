package com.bank.accounts.serivce.advice;

import com.bank.accounts.serivce.data.exception.AccountAmountNotZeroException;
import com.bank.accounts.serivce.data.exception.AccountNotFoundException;
import com.bank.commons.advice.BasicExceptionHandlingAdvice;
import com.bank.commons.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingAdvice extends BasicExceptionHandlingAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountAmountNotZeroException.class)
    public ResponseEntity<ExceptionResponse> accountAmountNotZeroHandler(
            AccountAmountNotZeroException exception) {
        log.warn("AccountNotFoundException handled. Message: " +
                exception.getMessage());

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(400)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseBody);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponse> accountNotFoundHandler(
            AccountNotFoundException exception) {
        log.warn("AccountNotFoundException handled. Message: " +
                exception.getMessage());

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(404)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseBody);
    }
}
