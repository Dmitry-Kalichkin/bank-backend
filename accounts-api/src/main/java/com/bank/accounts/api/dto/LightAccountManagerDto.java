package com.bank.accounts.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LightAccountManagerDto {
    private Long id;
    private UUID userId;
    private Double amount;
    private String type;
    private Boolean isClosed;
    private Boolean isBlocked;
}
