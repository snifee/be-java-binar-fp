package com.kostserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomKostDto {
    private Integer roomNumber;
    private List<String> imageUrl;
    private Boolean isAvailable;
    private String description;
    private Integer capacity;
    private Double pricePerCategory;
    private String priceCategory;
    private String roomType;
    private String roomFacility;
    private Integer kost;
}
