package com.kostserver.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddRatingRequest {
    @NotNull
    private Long room_id;
    @NotNull
    private Integer rating;
    @NotBlank(message = "Review cannot blank")
    private String review;
    @NotNull
    private Boolean anonym;
}
