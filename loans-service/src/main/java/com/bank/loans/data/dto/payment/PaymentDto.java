package com.bank.loans.data.dto.payment;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDto {
    private Long id;
    private Double amount;
    private Boolean isOverdue;
    private Date date;
}
