package com.kostserver.dto;

import lombok.Data;

@Data
public class ErrorResponseDto {

    private String status;
    private String message;
}
