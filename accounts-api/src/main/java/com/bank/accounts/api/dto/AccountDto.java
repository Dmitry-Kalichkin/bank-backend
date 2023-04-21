package com.bank.accounts.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountDto {
    private Long id;
    private Double amount;
    private String type;
    private Date creationDate;
    private Date closedDate;
    private Date blockedDate;
}
