package com.axis.task.model.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateAccountResponse {
    private String email;
    private String accountId;
}
