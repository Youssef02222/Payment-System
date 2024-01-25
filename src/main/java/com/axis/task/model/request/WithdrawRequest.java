package com.axis.task.model.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WithdrawRequest {
    @NotBlank(message = "account id is required")
    private String accountId;
    @NotBlank(message = "amount is required")
    private Double amount;
}
