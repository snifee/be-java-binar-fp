package com.kostserver.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_rooms")
public class Room extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    private int capacity;
    private Double price;
    private Boolean available;
    private String photoUrl;
    private String rentalScheme;

    @ManyToOne
    private UserProfile ownerId;

    @ManyToOne
    private RoomType roomTypeId;

    @OneToMany
    private List<Facility> facilityId;
}
