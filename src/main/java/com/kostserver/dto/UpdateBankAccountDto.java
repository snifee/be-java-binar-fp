package com.kostserver.dto;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateBankAccountDto {

    @NotBlank(message = "bank name must filled")
    private String bank_name;

    @NotBlank(message = "Account number cannot blank")
    @Pattern(regexp = "[0-9]+",message = "must receive number")
    private String account_number;

    @NotBlank(message = "account name must filled")
    private String account_name;
}
