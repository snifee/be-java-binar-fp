package com.kostserver.model.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_rooms")
public class RoomKost extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer roomNumber;
    private Boolean isAvailable;
    private String description;
    private Integer capacity;
    private Double pricePerCategory;

    @ManyToOne
    private RoomPriceCategory roomPriceCategory;

    @OneToOne
    private RoomImage roomImage;

    @ManyToOne
    private RoomType roomType;

    @ManyToMany
    @JoinTable(
            name = "room_kostxfacility",
            joinColumns = @JoinColumn(name ="room_kost_id"),
            inverseJoinColumns = @JoinColumn(name = "facilities_id")
    )
    private Set<RoomFacility> roomFacilities;

    @ManyToOne
    private Kost kost;
}
