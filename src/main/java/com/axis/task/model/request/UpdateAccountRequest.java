package com.axis.task.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpdateAccountRequest  {
    @NotBlank(message = "accountId is mandatory")
    private String accountId;
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "email is mandatory")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "phone is mandatory")
    @Pattern(regexp = "^01\\d{9}$", message = "Invalid phone number. It should start with '01' followed by 9 digits.")
    private String phone;
    public UpdateAccountRequest() {
    }

}
