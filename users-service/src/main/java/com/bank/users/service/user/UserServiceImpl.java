package com.bank.users.service.user;

import com.bank.users.data.entity.UserEntity;
import com.bank.users.data.enums.Authority;
import com.bank.users.data.exception.ForbiddenActionException;
import com.bank.users.data.mapper.UserMapper;
import com.bank.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bank.users.data.dto.CreateUserDto;
import com.bank.users.data.dto.UserDto;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user with email " +
                        email + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getByEmail(String email) {
        return userMapper.toDto(userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user with email " +
                        email + " not found")));
    }

    @Override
    @Transactional
    public void block(String email, UUID id) {
        UserEntity managerEntity = loadUserByUsername(email);
        UserEntity blockableUser = userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user with email " +
                        id + " not found"));

        if (managerEntity.getCreationDate().before(blockableUser.getCreationDate())) {

            blockableUser.setIsLocked(true);
        } else {
            if (blockableUser.getAuthorities().size() == 1
            && blockableUser.getAuthorities()
                    .stream().findFirst().get().equals(Authority.USER)) {
                blockableUser.setIsLocked(true);
            } else {
                throw new ForbiddenActionException("you can't block this user");
            }
        }
    }

    @Override
    @Transactional
    public UUID register(CreateUserDto createUserDto) {
        UserEntity userEntity = userMapper.toEntity(createUserDto);

       return userRepository.save(userEntity).getId();
    }
}