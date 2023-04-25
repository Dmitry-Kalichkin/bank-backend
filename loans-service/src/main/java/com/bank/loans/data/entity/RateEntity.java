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
@Table(name = "rates")
public class RateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "manager_id")
    private UUID managerId;

    @Column(name = "interest_rate", columnDefinition = "NUMERIC(12,2)")
    private Double interestRate;

    @Column(name = "created")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private Date creationDate;

    @OneToMany(mappedBy = "rate", cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    private List<LoanEntity> loans;
}
