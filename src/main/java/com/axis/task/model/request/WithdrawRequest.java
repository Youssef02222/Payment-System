package com.axis.task.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class WithdrawRequest {
    private String accountId;
    private Double amount;
}
