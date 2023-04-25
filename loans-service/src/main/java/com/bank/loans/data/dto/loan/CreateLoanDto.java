package com.bank.loans.data.dto.loan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
public class CreateLoanDto {
    @NotNull(message = "userId is required")
    private UUID userId;

    @NotNull(message = "managerId is required")
    private UUID managerId;

    @Range(min = 1, max = 360,
            message = "loan can be given for a period from 1 month to 30 years")
    @NotNull(message = "loan length is required")
    private Short length;

    @Min(value = 10000, message = "amount can't be less than 10 000 RUB")
    @NotNull(message = "amount is required")
    private Double amount;

    @Min(1)
    @NotNull(message = "rateId is required")
    private Long rateId;
}