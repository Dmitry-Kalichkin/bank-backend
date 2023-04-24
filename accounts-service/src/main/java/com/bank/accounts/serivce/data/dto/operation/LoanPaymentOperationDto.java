package ru.bank.accounts.data.dto.operation;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class LoanPaymentOperationDto extends CreateOperationDto {
    @Min(value = 1L, message = "minimal payment id is 1")
    private Long paymentId;
}
