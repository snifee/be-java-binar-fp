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
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@Service
public class DatabaseSeeder implements ApplicationRunner {
    @Autowired
    private KostPaymentSchemeRepository kostPaymentSchemeRepository;

    @Autowired
    private KostRuleRepo kostRuleRepo;
    @Autowired
    private RatingRepository ratingRepository;

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

    String[] emails = { "admin@mail.com", "user1@mail.com", "user2@mail.com" };
    EnumRole[] roles = { EnumRole.ROLE_USER_PEMILIK, EnumRole.ROLE_USER_PENCARI, EnumRole.ROLE_SUPERUSER };
    String defaultPassword = "password";

    Faker faker = new Faker();

    private void insertToAccountTable() {
        // SUPERADMIN
        Role roleAdmin = roleRepository.findByName(EnumRole.ROLE_SUPERUSER).get();
        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(roleAdmin);

        Account account0 = new Account();
        account0.setEmail("super@admin.com");
        account0.setPassword(passwordEncoder.encode(defaultPassword));
        account0.setPhone(faker.phoneNumber().phoneNumber());
        account0.setRoles(rolesAdmin);
        accountRepository.save(account0);
        UserProfile userProfile0 = new UserProfile();
        userProfile0.setAccount(account0);
        userProfile0.setFullname("ADMIN");
        userProfile0.setAddress("ADMIN");
        userProfile0.setGender(EnumGender.MALE);
        userProfile0.setOccupation("ADMIN");
        userProfile0
                .setPhotoUrl("http://res.cloudinary.com/dkmgqnqnw/image/upload/v1674291650/ra23gljkpqxyrsly2d0m.webp");

        userProfileRepository.save(userProfile0);
        account0.setUserProfile(userProfile0);
        accountRepository.save(account0);

        // ========================================================================
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
        userProfile1.setOccupation("occupation");
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
        roomKost11.setLabel("kost terbaru");
        roomKost11.setPrice(500000D);
        roomKost11.setName("Kamar AC");
        List<String> images = Arrays.asList("image1", "image2", "image3");
        roomKost11.setImageUrl(images);
        roomKost11.setKost(kost1);
        roomKostRepository.save(roomKost11);

        kost1.getRoomKosts().add(roomKost11);

        RoomKost roomKost12 = new RoomKost();
        roomKost12.setIsAvailable(true);
        roomKost12.setQuantity(2);
        roomKost12.setLabel("kost terhot");
        roomKost12.setPrice(200000D);
        roomKost12.setName("Kamar Punya Helikopter");
        roomKost12.setKost(kost1);
        roomKost12.setImageUrl(images);
        roomKostRepository.save(roomKost12);

        kost1.getRoomKosts().add(roomKost12);

        kostRepository.save(kost1);

        Kost kost2 = new Kost();
        kost2.setAddress(userProfile3.getAddress());
        kost2.setKostName("Kost " + userProfile3.getFullname() + " 2");
        kost2.setOwner(account3);
        kost2.setKostType(EnumKostType.PUTRI);
        kostRepository.save(kost2);

        RoomKost roomKost13 = new RoomKost();
        roomKost13.setIsAvailable(true);
        roomKost13.setQuantity(2);
        roomKost13.setLabel("kost tehit");
        roomKost13.setPrice(900000D);
        roomKost13.setName("Kamar Macan");
        roomKost13.setKost(kost2);
        roomKostRepository.save(roomKost13);

        kost2.getRoomKosts().add(roomKost13);

        kostRepository.save(kost1);

        account3.getKosts().add(kost2);
        accountRepository.save(account3);

        Rating rating1 = new Rating();
        rating1.setRating(4);
        rating1.setRoomKost(roomKost11);
        rating1.setAccount(account1);
        rating1.setUlasan("keren");
        ratingRepository.save(rating1);

        Rating rating2 = new Rating();
        rating2.setRating(5);
        rating2.setRoomKost(roomKost12);
        rating2.setAccount(account2);
        rating2.setUlasan("mantulity");
        ratingRepository.save(rating2);

        Rating rating3 = new Rating();
        rating3.setRating(2);
        rating3.setRoomKost(roomKost12);
        rating3.setAccount(account3);
        rating3.setUlasan("mantulity");
        ratingRepository.save(rating3);

        // Rating rating4 = new Rating();
        // rating4.setRating(2);
        // rating4.setRoomKost(roomKost13);
        // rating4.setAccount(account3);
        // rating4.setUlasan("mantulity");
        // ratingRepository.save(rating4);

    }

    private void insertToRoleTable() {
        Arrays.asList(roles).forEach((role) -> {
            Boolean existRole = roleRepository.existsByName(role);
            if (!existRole) {
                Role newRole = new Role();
                newRole.setName(role);

                roleRepository.save(newRole);
            }
        });
    }

    private void insertToKostRuleTable() {
        KostRule rule1 = new KostRule();
        rule1.setRule("Dilarang membawa binatang");
        KostRule rule2 = new KostRule();
        rule2.setRule("Dilarang membawa narkoba");
        KostRule rule3 = new KostRule();
        rule3.setRule("Kost tutup jam 10 malam");
        KostRule rule4 = new KostRule();
        rule4.setRule("Wajib lapor jika membawa teman");

        kostRuleRepo.save(rule1);
        kostRuleRepo.save(rule2);
        kostRuleRepo.save(rule3);
        kostRuleRepo.save(rule4);
    }

    private void insertToKostPaymentScheme() {
        KostPaymentScheme kostPaymentScheme = new KostPaymentScheme();
        kostPaymentScheme.setPayment_scheme("HARIAN");
        kostPaymentSchemeRepository.save(kostPaymentScheme);
        KostPaymentScheme kostPaymentScheme2 = new KostPaymentScheme();
        kostPaymentScheme2.setPayment_scheme("MINGGUAN");
        kostPaymentSchemeRepository.save(kostPaymentScheme2);
        KostPaymentScheme kostPaymentScheme3 = new KostPaymentScheme();
        kostPaymentScheme3.setPayment_scheme("BULANAN");
        kostPaymentSchemeRepository.save(kostPaymentScheme3);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Account> superuser = accountRepository.findByEmail("super@admin.com");

        if (!superuser.isPresent()) {
            insertToRoleTable();
            insertToAccountTable();
            insertToKostRuleTable();
            insertToKostPaymentScheme();
        }
    }
}