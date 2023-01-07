package com.kostserver.model;

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
@Entity(name = "tbl_transactions")
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numOfPeople;

    private Date transactionDate;
    private Date startRent;
    private Double price;
    private String status;
    private int rentalDuration;
    private String rentalScheme;
    private String urlDocument;

    @ManyToOne
    private Account accountId;
    @ManyToOne
    private Room roomId;

    @ManyToOne
    private Payment paymentId;
}
