package com.bank.loans.repository;

import com.bank.loans.data.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface PaymentRepository
        extends PagingAndSortingRepository<PaymentEntity, Long>,
        JpaRepository<PaymentEntity, Long> {
    Page<PaymentEntity> getAllByLoanId(Long loanId, Pageable pageable);
    Page<PaymentEntity> getAllByLoanIdAndIsOverdue(Long loanId, Boolean isOverdue, Pageable pageable);
    List<PaymentEntity> getByDateAndIsOverdue(Date date, Boolean isOverdue, Pageable pageable);
}