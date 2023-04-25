package com.bank.users.controller;

import com.bank.users.data.dto.CreateUserDto;
import com.bank.users.data.dto.UserDto;
import com.bank.users.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Users", description = "endpoints for users")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    @Secured("USER")
    public UserDto get(Principal principal) {
       return userService.getByEmail(principal.getName());
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public UserDto getByEmailForManagers(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID register(@RequestBody @Valid CreateUserDto createUserDto) {
        return userService.register(createUserDto);
    }

    @PatchMapping("/block/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public void block(Principal principal, @PathVariable UUID id) {
        userService.block(principal.getName(), id);
    }
}