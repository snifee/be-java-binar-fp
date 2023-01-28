package com.kostserver.dto;

import lombok.Data;

@Data
public class SuccessResponseDto {
    private String status;
    private String message = "success";
    private Object data;
}
