package com.bank.users.data.entity;

import com.bank.users.data.enums.Authority;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "credit_rating")
    @ColumnDefault("100")
    private Short creditRating;

    @Enumerated(EnumType.STRING)
    @Column(name = "authorities", columnDefinition = "authority[]")
    private Set<Authority> authorities;

    @Column(name = "created")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private Date creationDate;

    @Column(name = "is_locked")
    @ColumnDefault("false")
    private Boolean isLocked;

    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
