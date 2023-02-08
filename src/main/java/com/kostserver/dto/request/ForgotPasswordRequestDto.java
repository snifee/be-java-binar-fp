package com.kostserver.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ForgotPasswordRequestDto {
    @NotBlank
    @Email(message = "The email address is invalid.", regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

}
