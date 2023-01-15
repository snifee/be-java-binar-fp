package com.kostserver.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kost")
public class Kost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kostName;
    private String additionalKostRule;
    private String frontPhotoUrl;
    private String backPhotoUrl;


    @ManyToOne
    private Account ownerId;

    @OneToMany
    private Set<RoomKost> roomKosts = new HashSet<>();

    @OneToOne
    private KostLocation kostLocation;

    @OneToOne
    private Thumbnail thumbnail;

    @ManyToMany
    @JoinTable(name = "kost_rules",
            joinColumns = @JoinColumn(name = "kost_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id"))
    private Set<KostRule> kostRuleId= new HashSet<>();

    @ManyToMany
    @JoinTable(name = "kost_facilities",
            joinColumns = @JoinColumn(name = "kost_id"),
            inverseJoinColumns = @JoinColumn(name = "kost_facility_id"))
    private Set<KostFacility> kostFacilities = new HashSet<>();
}
