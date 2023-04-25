package com.bank.loans.data.dto.loan;

import lombok.Data;

@Data
public class LightLoanDto {
    private Long id;
    private Double amount;
    private Double amountRemain;
    private Boolean isClosed;
    private Double rate;
}
