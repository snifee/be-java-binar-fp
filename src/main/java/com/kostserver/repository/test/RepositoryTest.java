package com.kostserver.repository.test;

import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.UserProfile;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.UserProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Test
    public void test(){
//        Account account = new Account();
//        account.setEmail("mail@gmail.com");
//        accountRepository.save(account);
//
//        UserProfile userProfile = new UserProfile();
////        userProfile.setAccountId(account);
//
//        userProfileRepository.save(userProfile);
//        System.out.println(userProfile.getCreatedDate());

        EnumKostType enumKostType = EnumKostType.valueOf("PUTRA");
        System.out.println(enumKostType);



    }

}
