package com.kostserver.dto;

import lombok.Data;

@Data
public class RatingDto {
    String reviewText;
    Integer rating;
    Long roomId;
}
