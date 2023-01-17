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

    private String email;
    private String password;
    private String phone;
    private Boolean verified;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToOne(mappedBy = "accountId")
    private UserProfile userProfileId;

    @OneToOne
    private UserValidation userValidationId;

    @OneToOne
    private UserBank userBankId;

    @OneToMany(mappedBy = "accountId")
    private Set<Transaction> transactions;


}
