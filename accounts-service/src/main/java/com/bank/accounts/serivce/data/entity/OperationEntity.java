package com.bank.accounts.serivce.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Data
@Entity
@Table(name = "operations")
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id", referencedColumnName = "id")
    private AccountEntity destinationAccount;

    @Column(name = "is_successfully")
    private Boolean isSuccessfully;

    @Column(columnDefinition = "NUMERIC(14, 2)")
    private Double amount;

    @Column(name = "date")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private Date date;
}
