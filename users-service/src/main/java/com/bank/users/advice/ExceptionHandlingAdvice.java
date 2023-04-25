package com.bank.users.advice;

import com.bank.commons.advice.BasicExceptionHandlingAdvice;
import com.bank.commons.exception.ExceptionResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class ExceptionHandlingAdvice extends BasicExceptionHandlingAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> rateNotFoundHandler(
            UsernameNotFoundException exception) {
        log.warn("UsernameNotFoundException handled. Message: " +
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
