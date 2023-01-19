package com.kostserver.model.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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

    private Double price;
    private boolean indoorBathroom;
    private Integer roomNumber;
    private Boolean isAvailable;
    private String description;
    private Integer capacity;
    private String name;
    private Double width;
    private Double length;
    private int quantity;
    private int availableRoom;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "room_image",joinColumns = @JoinColumn(name = "room_id"))
    private List<String> imageUrl;

    @OneToOne
    private Thumbnail thumbnail;

    @ManyToMany
    @JoinTable(
            name = "room_kost_facility",
            joinColumns = @JoinColumn(name ="room_kost_id"),
            inverseJoinColumns = @JoinColumn(name = "facilities_id")
    )
    private Set<RoomFacility> roomFacilities;

    @ManyToOne
    private Kost kost;

    @ManyToOne
    private Account ownerId;

    @OneToMany
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany
    private Set<AdditionalRoomFacility> additionalRoomFacilities = new HashSet<>();
}
