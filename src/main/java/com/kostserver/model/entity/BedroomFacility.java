package com.kostserver.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_bedroom_facility")
public class BedroomFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String facilityName;
    private Boolean isActive;

    @ManyToMany(mappedBy = "bedroomFacilitiesId")
    private Set<RoomKost> roomKosts;

}
