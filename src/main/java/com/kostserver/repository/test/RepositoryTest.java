package com.kostserver.repository.test;

import com.kostserver.dto.ItemRoomDto;
import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.Transaction;
import com.kostserver.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryTest {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private KostRepository kostRepository;
    @Autowired
    private RoomKostRepository roomKostRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    @Test
    public void transactionRepo(){
        List<Transaction> list = transactionRepo.getAllTransactionAccount(4L);

        list.forEach(t ->{
            System.out.println(t.getId());
        });
    }

    @Test
    public void transactionRepoOwner(){
        List<Transaction> list = transactionRepo.getAllTransactionOwner(5L);

        list.forEach(t ->{
            System.out.println(t.getId());
        });
    }

    @Test
    public void kostRepoOwnerTest(){
        List<ItemRoomDto> list = roomKostRepository.getListRoomKostByOwner(79L);

        list.forEach(t ->{
            System.out.println(t.getId());
        });
    }

    @Test
    public void sumOfAvailableRoom(){
        Integer t = roomKostRepository.sumOfAvailableRoom(41L);

        System.out.println(t);

        Integer x = transactionRepo.sumOfOccupantsByOwner(79L);

        System.out.println(x);
    }



}
