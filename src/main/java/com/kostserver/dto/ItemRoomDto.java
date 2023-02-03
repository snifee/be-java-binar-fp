package com.kostserver.dto;

import com.kostserver.model.EnumKostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRoomDto {
    private Long id;
    private String name;
    private String label;
    private Double price;
    private String address;
    private EnumKostType type;
    private Double rating;
    private String thumbnail;
}
