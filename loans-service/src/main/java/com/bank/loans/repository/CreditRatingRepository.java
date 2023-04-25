package com.bank.loans.repository;

import com.bank.loans.data.entity.CreditRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface CreditRatingRepository extends JpaRepository<CreditRatingEntity, UUID> {
}
