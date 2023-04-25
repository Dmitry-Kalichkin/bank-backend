package com.bank.users.data.dto;

import com.bank.users.data.enums.Authority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class CreateUserDto {
    @NotBlank(message = "email can't be blank")
    @Email
    private String email;

    @NotBlank(message = "name can't be blank")
    private String name;

    @NotBlank(message = "password can't be blank")
    @Size(min = 8, message = "password must contain at least 8 characters")
    private String password;

    @NotNull(message = "authorities can't be null")
    private Set<Authority> authorities;
}
