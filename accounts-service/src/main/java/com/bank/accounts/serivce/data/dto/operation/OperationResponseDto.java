package ru.bank.accounts.data.dto.operation;

import lombok.Data;

@Data
public class OperationResponseDto {
    private long id;
    private boolean isSuccessfully;
}
