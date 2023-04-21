package com.bank.accounts.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateAccountDto {
    @NotNull(message = "user id cant be null")
    private UUID userId;
    @NotBlank(message = "type can't be blank")
    private String type;
}
