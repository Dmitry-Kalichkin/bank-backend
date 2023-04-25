package com.bank.loans.data.dto.account;

import lombok.Data;

import java.util.Date;

/*
    DTO for external service copied from service source code
 */

@Data
public class AccountDto {
    private Long id;
    private Double amount;
    private String type;
    private Date creationDate;
    private Boolean isBlocked;
    private Boolean isClosed;
}