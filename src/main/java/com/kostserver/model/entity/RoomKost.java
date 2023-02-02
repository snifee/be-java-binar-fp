package com.kostserver.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private Boolean indoorBathroom;
    private Boolean isAvailable;
//    private String description;
    private Integer maxPerson;
    private String name;
    private Double width;
    private Double length;
    private int quantity;
    private int availableRoom;

    @ElementCollection(targetClass = String.class,fetch = FetchType.LAZY)
    @CollectionTable(name = "room_image",joinColumns = @JoinColumn(name = "room_id"))
    private List<String> imageUrl = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,targetEntity = RoomFacility.class)
    @JoinTable(
            name = "room_kost_facilities",
            joinColumns = @JoinColumn(name ="room_kost_id"),
            inverseJoinColumns = @JoinColumn(name = "room_facilities_id",referencedColumnName = "id")
    )
    private Set<RoomFacility> roomFacilitiesId = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Kost kost;

    @JsonIgnore
    @ManyToOne
    private Account owner;

    @JsonIgnore
    @OneToMany(mappedBy = "roomKost",fetch = FetchType.EAGER)
    private Set<Rating> rating = new HashSet<>();

    @OneToMany(mappedBy = "roomKost",fetch = FetchType.EAGER)
    private Set<AdditionalRoomFacility> additionalRoomFacilities = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "roomKost")
    private Set<Transaction> transactions = new HashSet<>();
}
