package com.kostserver.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String phone;
    private String password;
}
