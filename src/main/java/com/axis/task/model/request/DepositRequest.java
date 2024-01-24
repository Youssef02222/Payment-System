package com.axis.task.model.request;

import lombok.Data;

@Data
public class DepositRequest {
    private String accountId;
    private Double amount;
}
