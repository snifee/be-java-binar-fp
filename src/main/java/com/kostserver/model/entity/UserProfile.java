package com.kostserver.model.entity;



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
    @Enumerated(EnumType.STRING)
    private EnumGender gender;
    private String occupation;
    private String photoUrl;
    @OneToOne
    private Account accountId;
}
