package com.kostserver.dto.request;

import com.kostserver.model.EnumIdCardType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserVerificationDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "(\\()?(\\+62|62|0)(\\d{2,3})?\\)?[ .-]?\\d{2,4}[ .-]?\\d{2,4}[ .-]?\\d{2,4}",message = "Invalid Phone Number")
    private String phone;

    @NotNull
    private EnumIdCardType type;

    @NotNull
    private MultipartFile photo;

}
