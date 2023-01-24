package com.kostserver.dto.request;

import com.kostserver.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private Double price;
    private Boolean isAvailable;
    private Integer maxPerson;
    private String name;
    private Double width;
    private Double length;
    private Integer quantity;
    private String[] images;
    private Boolean indoorBathroom;
    private Set<BathroomFacility> bathroomFacilitiesId;
    private Set<BedroomFacility> bedroomFacilitiesId;
    private Set<AdditionalRoomFacility> additionalRoomFacilities;
}
