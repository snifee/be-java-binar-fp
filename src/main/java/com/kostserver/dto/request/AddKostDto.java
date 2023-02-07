package com.kostserver.dto.request;

import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.KostPaymentScheme;
import com.kostserver.model.entity.KostRule;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddKostDto {

    @NotBlank
    private String name;
    @Pattern(regexp = "(^data:image/jpeg;base64,.*)|(^data:image/png;base64,.*)|(^data:image/jpg;base64,.*)"
            ,message = "only accept jpeg,png or jpg image")
    private String outdoor_photo;

    @Pattern(regexp = "(^data:image/jpeg;base64,.*)|(^data:image/png;base64,.*)|(^data:image/jpg;base64,.*)"
            ,message = "only accept jpeg,png or jpg image")
    private String indoor_photo;
    @NotNull
    private EnumKostType type;
    private String description;
    private String longitude;
    private String latitude;
    @NotBlank
    private String address;

    @NotBlank
    private String province;

    @NotBlank
    private String city;

    @NotBlank
    private String district;
    private String adress_note;

    private List<KostPaymentScheme> payment_scheme = new ArrayList<>();

    private List<KostRule> rules = new ArrayList<>();

    private String additional_rule;

}
