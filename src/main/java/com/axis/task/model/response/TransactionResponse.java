package com.axis.task.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TransactionResponse {
    private String accountId;
    private String transactionId;
    private Date transactionDate;
    private Double currentBalance;
}
