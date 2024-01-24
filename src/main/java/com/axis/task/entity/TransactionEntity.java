package com.axis.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String transactionId;
    private String type;
    private double amount;
    private Date transactionDate;
    private String status;

    @ManyToOne
    @JoinColumn(name="account_id")
    private AccountEntity accountDetails;
}
