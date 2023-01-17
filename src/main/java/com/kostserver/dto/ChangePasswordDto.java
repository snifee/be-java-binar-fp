package com.kostserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordDto {

    @NotBlank
    private String old_password;
    @NotBlank
    private String new_password;
    @NotBlank
    private String new_password2;
}
