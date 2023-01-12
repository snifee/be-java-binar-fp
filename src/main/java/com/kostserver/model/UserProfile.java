package com.kostserver.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_profile")
public class UserProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private Date birthDate;
    private String address;
    private int gender;
    private String job;
    private String phoneNumber;
    private String photoUrl;
    private String documentUrl;
    @OneToOne
    private Account accountId;
}
