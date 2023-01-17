package com.kostserver.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "additional_facility")
public class AdditionalFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    @ManyToOne
    private RoomKost roomKostId;
}
