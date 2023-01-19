package com.kostserver.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "tbl_room_facility")
public class RoomFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String facilityName;
    private Boolean isActive;

    @ManyToMany(mappedBy = "roomFacilities")
    private Set<RoomKost> roomKosts;
}
