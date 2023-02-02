package com.kostserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kostserver.model.EnumKostPaymentScheme;
import com.kostserver.model.EnumTransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_transaction")
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numOfPeople;
    private Date transactionDate;
    private Date startRent;
    private Date endRent;
    private Double price;
    @Enumerated(EnumType.STRING)
    private EnumTransactionStatus status;
    private int rentalDuration;
    @Enumerated(EnumType.STRING)
    private EnumKostPaymentScheme paymentScheme;
    private String paymentProof;
    private Double addonsFacilitiesPrice;

    @ManyToMany
    @JoinTable(name = "tbl_transaction_addons_facilities",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "addons_facility_id"))
    private Set<AdditionalRoomFacility> addonsFacilities = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    private Account account;
    @JsonIgnore
    @ManyToOne
    private RoomKost roomKost;
    @JsonIgnore
    @ManyToOne
    private Payment payment;
}
