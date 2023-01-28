package com.kostserver.model.entity;


import com.kostserver.model.EnumIdCardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tbl_user_validation")
public class UserValidation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idCardUrl;

    @Enumerated(EnumType.STRING)
    private EnumIdCardType type;

    @OneToOne
    private Account account;
}
