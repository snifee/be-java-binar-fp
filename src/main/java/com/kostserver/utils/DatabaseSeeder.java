package com.kostserver.utils;

import com.github.javafaker.Faker;
import com.kostserver.model.EnumGender;
import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.*;
import com.kostserver.model.EnumRole;
import com.kostserver.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@Service
public class DatabaseSeeder implements ApplicationRunner {
    @Autowired
    private KostPaymentSchemeRepository kostPaymentSchemeRepository;

    @Autowired
    private  KostRuleRepo  kostRuleRepo;

    @Autowired
    private RoomKostRepository roomKostRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private KostRepository kostRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    String[] emails = {"admin@mail.com", "user1@mail.com", "user2@mail.com"};
    EnumRole[] roles = {EnumRole.ROLE_USER_PEMILIK,EnumRole.ROLE_USER_PENCARI,EnumRole.ROLE_SUPERUSER};
    String defaultPassword = "password";

    Faker faker = new Faker();

    private void insertToAccountTable(){
        Role role1 = roleRepository.findByName(EnumRole.ROLE_USER_PEMILIK).get();
        Set<Role> roles1 = new HashSet<>();
        roles1.add(role1);

        Role role2 = roleRepository.findByName(EnumRole.ROLE_USER_PENCARI).get();
        Set<Role> roles2 = new HashSet<>();
        roles2.add(role2);

        Account account1 = new Account();
        account1.setEmail("user1@pemilik.com");
        account1.setPassword(passwordEncoder.encode(defaultPassword));
        account1.setPhone(faker.phoneNumber().phoneNumber());
        account1.setRoles(roles1);
        accountRepository.save(account1);
        UserProfile userProfile1 = new UserProfile();
        userProfile1.setAccount(account1);
        userProfile1.setFullname(faker.name().fullName());
        userProfile1.setAddress(faker.address().fullAddress());
        userProfile1.setGender(EnumGender.MALE);
        userProfileRepository.save(userProfile1);
        account1.setUserProfile(userProfile1);
        accountRepository.save(account1);

        Account account2 = new Account();
        account2.setEmail("user2@pemilik.com");
        account2.setPassword(passwordEncoder.encode(defaultPassword));
        account2.setPhone(faker.phoneNumber().phoneNumber());
        account2.setRoles(roles1);
        accountRepository.save(account2);
        UserProfile userProfile2 = new UserProfile();
        userProfile2.setAccount(account2);
        userProfile2.setFullname(faker.name().fullName());
        userProfile2.setAddress(faker.address().fullAddress());
        userProfile2.setGender(EnumGender.FEMALE);
        userProfileRepository.save(userProfile2);
        account2.setUserProfile(userProfile2);
        accountRepository.save(account2);

        Account account3 = new Account();
        account3.setEmail("user3@pencari.com");
        account3.setPassword(passwordEncoder.encode(defaultPassword));
        account3.setPhone(faker.phoneNumber().phoneNumber());
        account3.setRoles(roles2);
        accountRepository.save(account3);
        UserProfile userProfile3 = new UserProfile();
        userProfile3.setAccount(account3);
        userProfile3.setFullname(faker.name().fullName());
        userProfile3.setAddress(faker.address().fullAddress());
        userProfile3.setGender(EnumGender.MALE);
        userProfileRepository.save(userProfile3);
        account3.setUserProfile(userProfile3);
        accountRepository.save(account3);

        log.info(account3.getUserProfile().getFullname());

        Kost kost1 = new Kost();
        kost1.setAddress(userProfile3.getAddress());
        kost1.setKostName("Kost " + userProfile3.getFullname());
        kost1.setOwner(account3);
        kost1.setKostType(EnumKostType.PUTRA);
        kostRepository.save(kost1);

        account3.getKosts().add(kost1);
        accountRepository.save(account3);

        RoomKost roomKost11 = new RoomKost();
        roomKost11.setIsAvailable(true);
        roomKost11.setQuantity(2);
        roomKost11.setName("Kamar AC");
        roomKost11.setKost(kost1);
        roomKostRepository.save(roomKost11);

        kost1.getRoomKosts().add(roomKost11);

        RoomKost roomKost12 = new RoomKost();
        roomKost12.setIsAvailable(true);
        roomKost12.setQuantity(2);
        roomKost12.setName("Kamar Punya Helikopter");
        roomKost12.setKost(kost1);
        roomKostRepository.save(roomKost12);

        kost1.getRoomKosts().add(roomKost12);

        kostRepository.save(kost1);

        Kost kost2 = new Kost();
        kost2.setAddress(userProfile3.getAddress());
        kost2.setKostName("Kost " + userProfile3.getFullname()+ " 2");
        kost2.setOwner(account3);
        kost2.setKostType(EnumKostType.PUTRI);
        kostRepository.save(kost2);

        account3.getKosts().add(kost2);
        accountRepository.save(account3);
    }

    private void insertToRoleTable(){
        Arrays.asList(roles).forEach((role)->{
            Boolean existRole = roleRepository.existsByName(role);
            if (!existRole){
                Role newRole = new Role();
                newRole.setName(role);

                roleRepository.save(newRole);
            }
        });
    }

    private void insertToKostRuleTable(){
        KostRule rule1 = new KostRule();
        rule1.setRule("rule");
        KostRule rule2 = new KostRule();
        rule2.setRule("rule2");
        KostRule rule3 = new KostRule();
        rule3.setRule("rule3");
        KostRule rule4 = new KostRule();
        rule4.setRule("rule4");

        kostRuleRepo.save(rule1);
        kostRuleRepo.save(rule2);
        kostRuleRepo.save(rule3);
        kostRuleRepo.save(rule4);
    }

    private void insertToKostPaymentScheme(){
        KostPaymentScheme kostPaymentScheme = new KostPaymentScheme();
        kostPaymentScheme.setPayment_scheme("MINGGUAN");
        kostPaymentSchemeRepository.save(kostPaymentScheme);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        insertToRoleTable();
        insertToAccountTable();
        insertToKostRuleTable();
        insertToKostPaymentScheme();
    }
}