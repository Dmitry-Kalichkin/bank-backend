package com.bank.accounts.api.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class AccountManagerDto {
    private Long id;
    private UUID userId;
    private Double amount;
    private String type;
    private Date creationDate;
    private Date closedDate;
    private Date blockedDate;

}
