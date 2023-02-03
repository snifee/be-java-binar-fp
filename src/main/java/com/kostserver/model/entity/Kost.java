package com.kostserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kostserver.model.EnumKostPaymentScheme;
import com.kostserver.model.EnumKostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Entity(name = "tbl_kost")
public class Kost extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kostName;
    private String indoorPhotoUrl;
    private String outdoorPhotoUrl;
    @Enumerated(EnumType.STRING)
    private EnumKostType kostType;

    private String description;
    private String address;
    private String city;
    private String province;
    private String district;
    private String latitude;
    private String longitude;
    private String addressNote;
    private String additionalKostRule;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "kost_payment_schemes",
            joinColumns = @JoinColumn(name = "kost_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_scheme_id"))
    private Set<KostPaymentScheme> kostPaymentScheme = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    private Account owner;

    @JsonIgnore
    @OneToMany(mappedBy = "kost",fetch = FetchType.EAGER)
    private Set<RoomKost> roomKosts = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "kost_rules",
            joinColumns = @JoinColumn(name = "kost_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id"))
    private Set<KostRule> kostRule= new HashSet<>();

}
