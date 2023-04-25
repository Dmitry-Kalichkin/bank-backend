package com.bank.loans.repository;

import com.bank.loans.data.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findAllByUserId(UUID userId);
}
