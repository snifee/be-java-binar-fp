package com.kostserver.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends BaseEntity implements Serializable {
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
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @JsonIgnore
    @OneToOne(mappedBy = "account")
    private UserProfile userProfile;

    @JsonIgnore
    @OneToOne(mappedBy = "account")
    private UserValidation userValidation;

    @JsonIgnore
    @OneToOne(mappedBy = "account")
    private UserBank userBank;

}
