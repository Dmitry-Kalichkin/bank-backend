package com.bank.commons.advice;


import com.bank.commons.exception.ExceptionResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class BasicExceptionHandlingAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> matchingArgsHandler(
            MethodArgumentTypeMismatchException exception) {
        log.warn("MethodArgumentTypeMismatchException handled. Message: " +
                exception.getMessage());

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(400)
                .message("Type mismatch in request")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseBody);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> validationHandler(
            ValidationException exception) {
        log.warn("ValidationException handled. Message: " +
                exception.getMessage());

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(400)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseBody);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentValidationHandler(
            MethodArgumentNotValidException exception) {
        String message = exception.getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("MethodArgumentNotValidException handled. Message: " +
                message);

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(400)
                .message(message)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseBody);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> badJsonHandler(
            HttpMessageNotReadableException exception) {
        log.warn("HttpMessageNotReadableException handled. Message: " +
                Arrays.toString(exception.getStackTrace()));

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(400)
                .message("incorrect json input")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseBody);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> baseExceptionHandler(
            Exception exception) {
        log.warn("Unknown exception handled. Message: " +
                Arrays.toString(exception.getStackTrace()));

        ExceptionResponse responseBody = ExceptionResponse.builder()
                .timestamp(new Date())
                .code(500)
                .message("Internal server error")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseBody);
    }
}
