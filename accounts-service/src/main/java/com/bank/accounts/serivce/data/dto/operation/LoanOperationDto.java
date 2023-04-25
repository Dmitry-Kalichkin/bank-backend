package com.bank.accounts.serivce.data.dto.operation;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class LoanOperationDto extends CreateOperationDto {
    @Min(value = 1L, message = "minimal payment id is 1")
    private Long paymentId;
}
