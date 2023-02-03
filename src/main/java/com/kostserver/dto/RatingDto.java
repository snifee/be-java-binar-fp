package com.kostserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private Long id;
    private String name;
    private String photo;
    private Integer rating;
    private String review;
    private String occupation;
    private Boolean anonym;
}