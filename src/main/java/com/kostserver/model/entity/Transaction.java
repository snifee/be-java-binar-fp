package com.kostserver.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private String status;
    private int rentalDuration;
    private String rentalScheme;
    private String urlDocument;

    @ManyToOne
    private Account account;
    @ManyToOne
    private RoomKost roomKost;
    @ManyToOne
    private Payment payment;
}
