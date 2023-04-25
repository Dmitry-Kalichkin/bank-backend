package com.bank.users.data.dto;

import com.bank.users.data.enums.Authority;
import lombok.Data;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String name;
    private Short creditRating;
    private Set<Authority> authorities;
    private Date creationDate;
    private Boolean isLocked;
}
