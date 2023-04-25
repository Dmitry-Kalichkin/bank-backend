package com.bank.loans.data.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ExceptionResponse {
    private Integer code;
    private String message;
    private Date timestamp;
}
