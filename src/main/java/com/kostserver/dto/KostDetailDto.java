package com.kostserver.dto;

import com.kostserver.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KostDetailDto {
    private Long id;
    private Date createdDate;
    private Integer roomNumber;
    private RoomImage roomImage;
    private Boolean isAvailable;
    private String description;
    private Integer capacity;
    private Double pricePerCategory;
    private RoomPriceCategory roomPriceCategory;
    private RoomType roomType;
    private Set<RoomFacility> roomFacility;
    private Kost kost;

    public static KostDetailDto of (RoomKost data) {
        return KostDetailDto.builder()
                .id(data.getId())
                .createdDate(data.getCreatedDate())
                .roomNumber(data.getRoomNumber())
                .roomImage(data.getRoomImage())
                .roomPriceCategory(data.getRoomPriceCategory())
                .pricePerCategory(data.getPricePerCategory())
                .roomType(data.getRoomType())
                .roomFacility(data.getRoomFacilities())
                .kost(data.getKost())
                .build();
    }
}
