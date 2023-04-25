package com.bank.loans.advice;

import com.bank.commons.advice.BasicExceptionHandlingAdvice;
import com.bank.commons.exception.ExceptionResponse;
import com.bank.loans.data.exception.*;
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
    @ExceptionHandler(UserManagerSameIdException.class)
    public ResponseEntity<ExceptionResponse> samePersonLoanHandler(
            UserManagerSameIdException exception) {
        log.warn("UserManagerSameIdException handled. Message: " +
                exception.getLogMessage());

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(400)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseBody);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> paymentNotFoundHandler(
            PaymentNotFoundException exception) {
        log.warn("LoanNotExistsException handled. Message: " +
                exception.getMessage());

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(404)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseBody);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LoanNotExistsException.class)
    public ResponseEntity<ExceptionResponse> loanNotFoundHandler(
            LoanNotExistsException exception) {
        log.warn("LoanNotExistsException handled. Message: " +
                exception.getMessage());

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(404)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseBody);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RateNameExistsException.class)
    public ResponseEntity<ExceptionResponse> rateEntityAlreadyExistsHandler(
            RateNameExistsException exception) {
        log.warn("RateNameExistsException handled. Message: " +
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
    @ExceptionHandler(RateNotExistsException.class)
    public ResponseEntity<ExceptionResponse> rateNotFoundHandler(
            RateNotExistsException exception) {
        log.warn("RateNotExistsException handled. Message: " +
                exception.getMessage());

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(404)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseBody);
    }
}