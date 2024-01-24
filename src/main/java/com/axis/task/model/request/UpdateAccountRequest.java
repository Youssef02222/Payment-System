package com.axis.task.model.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpdateAccountRequest {
    private String accountId;
    private String name;
    private String email;
    private String phone;
}
