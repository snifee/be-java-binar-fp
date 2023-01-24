package com.kostserver.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_bathroom_facility")

public class BathroomFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String facilityName;
    private Boolean isActive;

    @ManyToMany(mappedBy = "bathroomFacilitiesId")
    private Set<RoomKost> roomKosts;
}
