package com.bank.users.data.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    USER("USER"),
    MANAGER("MANAGER");

    private String name;

    Authority(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
