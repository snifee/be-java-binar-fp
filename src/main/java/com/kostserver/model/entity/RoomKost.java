package com.kostserver.model.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_room")
public class RoomKost extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private String label;
    private Integer roomNumber;
    private Boolean isAvailable;
    private String description;
    private Integer maxPerson;
    private String name;
    private Double width;
    private Double length;
    private Integer quantity;
    private Integer availableRoom;
    private String[] images;
    private Boolean indoorBathroom;

    @ManyToMany
    @JoinTable(
            name = "room_kost_facilities",
            joinColumns = @JoinColumn(name ="room_kost_id"),
            inverseJoinColumns = @JoinColumn(name = "room_facilities_id")
    )
    private Set<RoomFacility> roomFacilitiesId;
    @ManyToMany
    @JoinTable(
            name = "bathroom_kost_facilities",
            joinColumns = @JoinColumn(name ="room_kost_id"),
            inverseJoinColumns = @JoinColumn(name = "bathroom_facilities_id")
    )
    private Set<BathroomFacility> bathroomFacilitiesId;

    @ManyToMany
    @JoinTable(
            name = "bedroom_kost_facilities",
            joinColumns = @JoinColumn(name ="room_kost_id"),
            inverseJoinColumns = @JoinColumn(name = "bedroom_facilities_id")
    )
    private Set<BedroomFacility> bedroomFacilitiesId;

    @ManyToOne
    private Kost kost;

    @ManyToOne
    private Account owner;

    @OneToMany(mappedBy = "roomKost")
    private Set<Rating> rating = new HashSet<>();

    @OneToMany(mappedBy = "roomKost")
    private Set<AdditionalRoomFacility> additionalRoomFacilities = new HashSet<>();

    @OneToMany(mappedBy = "roomKost")
    private Set<Transaction> transactions = new HashSet<>();
}
