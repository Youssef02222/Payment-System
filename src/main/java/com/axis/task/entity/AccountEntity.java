package com.axis.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "accounts")
public class AccountEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String accountId;
    private String name;
    private String phone;
    private String email;
    private double balance;
    @OneToMany(mappedBy="accountDetails", cascade=CascadeType.ALL)
    private List<TransactionEntity> transactions;

}
