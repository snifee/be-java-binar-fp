package com.kostserver.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String phone;
    private Boolean verified = false;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "account")
    private UserProfile userProfile;
    @OneToOne(mappedBy = "account")
    private UserValidation userValidation;
    @OneToOne(mappedBy = "account")
    private UserBank userBank;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "owner")
    private Set<Kost> kosts = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private Set<Rating> ratings = new HashSet<>();

}
