package com.axis.task.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeleteAccountRequest {
    @NotBlank(message = "account id is required")
    private String accountId;

}
