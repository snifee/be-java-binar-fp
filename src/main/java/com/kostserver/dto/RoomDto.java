package com.kostserver.dto;

import java.util.List;

import com.kostserver.model.EnumKostType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private String label;
    private Double price;
    // private List<String> imageUrl;
    private String address;
    private EnumKostType type;
    private Double rating;

}
