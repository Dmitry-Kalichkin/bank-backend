package com.bank.loans.data.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "loans")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "manager_id")
    private UUID managerId;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "length")
    private Short length;

    @Column(name = "amount", columnDefinition = "NUMERIC(12,2)")
    private Double amount;

    @Column(name = "amount_remain", columnDefinition = "NUMERIC(12,2)")
    private Double amountRemain;

    @Column(name = "created")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private Date creationDate;

    @Column(name = "last_payment")
    @Setter(AccessLevel.NONE)
    private Date lastPaymentDate;

    @Column(name = "closed")
    private Boolean isClosed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_id", referencedColumnName = "id")
    private RateEntity rate;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    private List<PaymentEntity> payments;
}
