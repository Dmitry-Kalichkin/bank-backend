package com.bank.accounts.serivce.data.dto.operation;

import lombok.Data;

import java.util.Date;

@Data
public class OperationDto {
    private Long id;
    private Long accountId;
    private Long destinationAccountId;
    private Boolean isSuccessfully;
    private Double amount;
    private Date date;
}
