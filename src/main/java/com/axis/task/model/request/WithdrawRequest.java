package com.axis.task.model.request;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WithdrawRequest {
    private String accountId;
    private Double amount;
}
