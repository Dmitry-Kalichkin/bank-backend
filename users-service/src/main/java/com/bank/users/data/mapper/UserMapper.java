package com.bank.users.data.mapper;

import com.bank.users.data.dto.CreateUserDto;
import com.bank.users.data.dto.UserDto;
import com.bank.users.data.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper mapper;

    @PostConstruct
    private void configureMapper() {
        mapper.createTypeMap(UserEntity.class, UserDto.class);
        mapper.createTypeMap(CreateUserDto.class, UserEntity.class)
                .addMapping(source -> false, UserEntity::setIsLocked)
                .addMapping(source -> (short) 100, UserEntity::setCreditRating);
    }

    public UserDto toDto(UserEntity userEntity) {
        return Objects.isNull(userEntity) ? null :
                mapper.map(userEntity, UserDto.class);
    }

    public UserEntity toEntity(CreateUserDto createUserDto) {
        return Objects.isNull(createUserDto) ? null :
                mapper.map(createUserDto, UserEntity.class);
    }
}
