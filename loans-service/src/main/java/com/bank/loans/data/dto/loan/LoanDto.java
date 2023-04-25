package com.bank.loans.data.dto.loan;

import com.bank.loans.data.dto.rate.RateDto;
import lombok.Data;

import java.util.Date;

@Data
public class LoanDto {
    private Long id;
    private String userId;
    private String managerId;
    private Long accountId;
    private Short length;
    private Double amount;
    private Double amountRemain;
    private Date creationDate;
    private Date lastPaymentDate;
    private Boolean isClosed;
    private RateDto rate;
}
