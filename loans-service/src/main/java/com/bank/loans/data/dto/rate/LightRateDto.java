package com.bank.loans.data.dto.rate;

import lombok.Data;

@Data
public class LightRateDto {
    private Long id;
    private String name;
    private Double interestRate;
}
