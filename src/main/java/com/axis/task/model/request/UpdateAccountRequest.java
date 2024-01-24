package com.axis.task.model.request;

import lombok.Data;

@Data
public class UpdateAccountRequest {
    private String accountId;
    private String name;
    private String email;
    private String phone;
}
