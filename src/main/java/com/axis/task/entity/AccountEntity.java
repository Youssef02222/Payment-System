package com.axis.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private long id;
    private String accountId;
    private String name;
    private String phone;
    private String email;
    private double balance;
    @OneToMany(mappedBy="accountDetails", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<TransactionEntity> transactions;

}
