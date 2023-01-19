package com.kostserver.model.entity;

import com.kostserver.model.EnumKostType;
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
@Table(name = "tbl_kost")
public class Kost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kostName;
    private String frontPhotoUrl;
    private String backPhotoUrl;
    @Enumerated(EnumType.STRING)
    private EnumKostType kostType;

    private String description;
    private String address;
    private String city;
    private String province;
    private String district;
    private Double latitude;
    private Double longitude;
    private String addressNote;
    private String additionalKostRule;

    @ManyToMany
    @JoinTable(name = "kost_payment_schemes",
            joinColumns = @JoinColumn(name = "kost_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_scheme_id"))
    private Set<KostPaymentScheme> kostPaymentScheme = new HashSet<>();


    @ManyToOne
    private Account owner;

    @OneToMany(mappedBy = "kost")
    private Set<RoomKost> roomKosts = new HashSet<>();

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
