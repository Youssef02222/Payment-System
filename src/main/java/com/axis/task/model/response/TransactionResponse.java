package com.axis.task.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionResponse {
    private String accountId;
    private String transactionId;
    private Date transactionDate;
    private Double currentBalance;
}
