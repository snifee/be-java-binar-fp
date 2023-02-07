package com.kostserver.dto.request;

import com.kostserver.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private Long kost_id;

    private Double price;
    private Boolean is_available;
    private Integer max_person;
    private String name;
    private Double width;
    private Double length;
    private Integer quantity;
    private List<@Pattern(regexp = "(^data:image/jpeg;base64,.*)|(^data:image/png;base64,.*)|(^data:image/jpg;base64,.*)"
            ,message = "only accept jpeg,png or jpg image")
            String> images = new ArrayList<>();
    private Boolean indoor_bathroom;
    private Set<RoomFacility> bathroom_facilities = new HashSet<>();
    private Set<RoomFacility> bedroom_facilities = new HashSet<>();
    private Set<AdditionalRoomFacility> addons_facilities = new HashSet<>();
}
