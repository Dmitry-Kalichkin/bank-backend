package com.bank.loans.repository;

import com.bank.loans.data.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<RateEntity, Long> {
    boolean existsByName(String name);
    Optional<RateEntity> findByName(String name);
}
