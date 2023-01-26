package com.kostserver.repository.test;

import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.RoomKost;
import com.kostserver.model.entity.UserProfile;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.RoomKostRepository;
import com.kostserver.repository.UserProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private RoomKostRepository roomKostRepository;

    @Test
    public void test(){
        Account account = new Account();
        account.setEmail("mail@gmail.com");
        accountRepository.save(account);

        UserProfile userProfile = new UserProfile();
//        userProfile.setAccountId(account);

        userProfileRepository.save(userProfile);
        System.out.println(userProfile.getCreatedDate());


    }

    @Test
    public void testSearchRoom(){

        Pageable pageable = PageRequest.of(0, 2);

        List<RoomKost> roomList = roomKostRepository.searchRoom("kamar","kost", EnumKostType.PUTRA,0.0,999999.0,pageable);

        roomList.stream().forEach(room->{
            System.out.println("Nama Kamar   :" + room.getName());
            System.out.println("Nama Kost    :" + room.getKost().getKostName());
            System.out.println("Type         :" + room.getKost().getKostType());
        });


    }

}
