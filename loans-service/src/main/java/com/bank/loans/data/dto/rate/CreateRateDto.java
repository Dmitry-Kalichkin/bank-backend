package com.bank.loans.data.dto.rate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
public class CreateRateDto {

    @NotBlank(message = "name can't be blank")
    private String name;

    @NotNull(message = "managerId is required")
    private UUID managerId;

    @NotNull(message = "interestRate is required")
    @Range(min = 1, max = 30, message = "not realistic interest rate")
    private Double interestRate;
}
