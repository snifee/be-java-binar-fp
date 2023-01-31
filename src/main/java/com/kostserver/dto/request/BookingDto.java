package com.kostserver.dto.request;

import com.kostserver.model.EnumKostPaymentScheme;
import com.kostserver.model.entity.AdditionalRoomFacility;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookingDto {
    private Long room_id;
    private Long user_id;
    private Integer capacity;
    private String start_date;
    private String payment_scheme;

    private List<AdditionalRoomFacility> addons_facilities = new ArrayList<>();
}
