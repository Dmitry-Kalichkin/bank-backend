package com.bank.users.service.user;



import com.bank.users.data.dto.CreateUserDto;
import com.bank.users.data.dto.UserDto;

import java.util.UUID;

public interface UserService {
    UUID register(CreateUserDto createUserDto);
    UserDto getByEmail(String email);
    void block(String email, UUID id);

}
