package com.kostserver.dto;

import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.KostPaymentScheme;
import com.kostserver.model.entity.KostRule;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddKostRequestDto {
    @NotBlank
    private String name;

    private MultipartFile image_1;

    private MultipartFile image_2;

    @NotBlank
    private EnumKostType type;

    private String description;

    private List<KostPaymentScheme> payment_scheme = new ArrayList<>();

    private List<KostRule> rules = new ArrayList<>();

    private String additional_rule;
}
