package com.axis.task.model.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DepositRequest {
    private String accountId;
    private Double amount;

}
