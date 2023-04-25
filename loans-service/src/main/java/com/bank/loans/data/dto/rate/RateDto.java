package com.bank.loans.data.dto.rate;

import lombok.Data;

import java.util.Date;

@Data
public class RateDto {
    private Long id;
    private String name;
    private String managerId;
    private Double interestRate;
    private Date creationDate;
}
