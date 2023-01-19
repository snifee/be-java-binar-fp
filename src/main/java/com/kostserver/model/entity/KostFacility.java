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

@Entity(name = "tbl_kost_facility")
public class KostFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String facilityName;

    private String type;

    @ManyToMany(mappedBy = "kostFacilities")
    private Set<Kost> kosts;

}