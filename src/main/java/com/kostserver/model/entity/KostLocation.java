package com.kostserver.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kost_location")
public class KostLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String rtrw;
    private String urbanVillage;
    private String subDistrict;
    private String postalCode;
    private String city;
    private String province;
    private Double latitude;
    private Double longitude;

}
