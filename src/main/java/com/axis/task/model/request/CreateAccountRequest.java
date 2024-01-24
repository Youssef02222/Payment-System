package com.axis.task.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateAccountRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Invalid email address")
    private String email;
    @Pattern(regexp = "^01\\d{9}$", message = "Invalid phone number. It should start with '01' followed by 9 digits.")
    private String phone;
}
