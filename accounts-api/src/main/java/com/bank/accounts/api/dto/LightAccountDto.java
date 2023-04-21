package com.bank.accounts.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LightAccountDto {
    private Long id;
    private Double amount;
    private String type;
    private Boolean isBlocked;
    private Boolean isClosed;
}
