package com.kostserver.model.request;

import com.kostserver.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateKostRequest {
    private Long id;
    private Integer roomNumber;
    private RoomImage roomImage;
    private Boolean isAvailable;
    private String description;
    private Integer capacity;
    private Double pricePerCategory;
    private RoomPriceCategory roomPriceCategory;
    private RoomType roomType;
    private Set<RoomFacility> roomFacility;
}
