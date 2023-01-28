package com.kostserver.model.response;

import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.Role;
import com.kostserver.model.entity.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class UserDetailsRespond {

    public UserDetailsRespond(Account account, UserProfile userProfile){
        this.id = account.getId();
        this.fullname = userProfile.getFullname()==null ? "" : userProfile.getFullname();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.gender = userProfile.getGender()==null ? "" : userProfile.getGender().name();
        this.occupation = userProfile.getOccupation()==null ? "" : userProfile.getOccupation();
        this.role = account.getRoles().iterator().next();
        this.verified = account.getVerified();
    }
    private Long id;
    private String fullname;
    private String email;
    private String phone;
    private String birthdate;
    private String gender;
    private String occupation;
    private Role role;
    private boolean verified;
}