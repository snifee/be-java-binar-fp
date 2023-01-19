package com.kostserver.model.entity;



import com.kostserver.model.EnumGender;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_user_profile")
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
    private Account account;
}
