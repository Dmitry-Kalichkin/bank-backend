package ru.bank.accounts.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(columnDefinition = "NUMERIC(14, 2)")
    private Double amount;

    @Column
    private String type;

    @Column(name = "creation_date")
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private Date creationDate;

    @Column(name = "closing_date")
    private Date closingDate;

    @Column(name = "blocking_date")
    private Date blockingDate;
}
