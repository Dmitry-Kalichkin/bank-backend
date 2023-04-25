package com.bank.loans.data.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.sql.Date;

@Data
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "amount", columnDefinition = "NUMERIC(12,2)")
    @Setter(AccessLevel.NONE)
    private Double amount;

    @Column(name = "is_overdue")
    private Boolean isOverdue;

    @Setter(AccessLevel.NONE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    @Setter(AccessLevel.NONE)
    private LoanEntity loan;
}
