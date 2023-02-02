package com.kostserver.dto;

import com.kostserver.model.EnumKostType;
import lombok.Data;

@Data
public class SearchRoomDto {
    private Long id;
    private String name;
    private String label;
    private Double price;
    private String address;
    private EnumKostType type;
    private Double rating;
    private String thumbnail;
}
