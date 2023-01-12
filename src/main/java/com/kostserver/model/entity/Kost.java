package com.kostserver.model.entity;

import com.kostserver.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kost")
public class Kost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kostName;

    @OneToOne
    private Account ownerId;

    @OneToMany
    private Set<RoomKost> roomKosts = new HashSet<>();

    @OneToOne
    private KostLocation kostLocation;

    @OneToOne
    private KostThumbnail kostThumbnail;


}
