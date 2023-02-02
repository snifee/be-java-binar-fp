package com.kostserver.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateTransactionStatusDto {

    @NotNull
    private Long id;

    @NotNull
    private String status;
}
