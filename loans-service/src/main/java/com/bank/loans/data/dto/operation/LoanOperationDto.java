package com.bank.loans.data.dto.operation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class LoanOperationDto {
    @Min(value = 1L, message = "minimal payment id is 1")
    private Long paymentId;

    @Min(value = 1L, message = "minimal account id value is 1")
    private Long accountId;

    @Min(value = 1L, message = "minimal account id value is 1")
    private Long destinationAccountId;

    @Min(value = 10, message = "minimal transfer amount is 10 RUB")
    @NotNull(message = "amount is required")
    private Double amount;
}
