package com.kostserver.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.kostserver.model.EnumGender;
import com.kostserver.model.EnumIdCardType;
import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.*;
import com.kostserver.model.EnumRole;
import com.kostserver.repository.*;
import com.kostserver.repository.test.UserValidationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Component
@Service
public class DatabaseSeeder implements ApplicationRunner {
    @Autowired
    private KostPaymentSchemeRepository kostPaymentSchemeRepository;

    @Autowired
    private AdditionalRoomFacilityRepo additionalRoomFacilityRepo;

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
    private UserBankRepo userBankRepo;

    @Autowired
    private UserValidationRepo userValidationRepo;

    @Autowired
    private KostRepository kostRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoomFacilityRepo roomFacilityRepo;

    String[] emails = {"admin@mail.com", "user1@mail.com", "user2@mail.com"};
    EnumRole[] roles = {EnumRole.ROLE_USER_PEMILIK,EnumRole.ROLE_USER_PENCARI,EnumRole.ROLE_SUPERUSER};
    String defaultPassword = "password";



    Random random = new Random();

    private void insertToAccountTableSuperadmin(){
        Faker faker = new Faker(new Random(24));
        //SUPERADMIN
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
        userProfile0.setPhotoUrl("http://res.cloudinary.com/dkmgqnqnw/image/upload/v1674291650/ra23gljkpqxyrsly2d0m.webp");

        userProfileRepository.save(userProfile0);
        account0.setUserProfile(userProfile0);
        accountRepository.save(account0);
    }

    private void insertToAccountTablePemilik(){
        Long randomId = (long)random.nextInt((5 - 1) + 1) + 1;

        Role role1 = roleRepository.findByName(EnumRole.ROLE_USER_PEMILIK).get();
        Set<Role> roles1 = new HashSet<>();
        roles1.add(role1);

        for (int i=0;i<1;i++){
            Faker faker = new Faker( new Locale("en-US"),new Random(randomId));
            Integer r = random.nextInt();
            String email = faker.bothify(faker.name().lastName()+".????"+r+"@pemilik.com");
            Optional<Account> checkAccount = accountRepository.findByEmail(email);

            if (checkAccount.isPresent()){
                continue;
            }
            Account accountPemilik = new Account();
            accountPemilik.setEmail(email);
            accountPemilik.setPassword(passwordEncoder.encode(defaultPassword));
            accountPemilik.setPhone(faker.phoneNumber().phoneNumber());
            accountPemilik.setRoles(roles1);
            accountRepository.save(accountPemilik);

            UserProfile userProfile1 = new UserProfile();
            userProfile1.setAccount(accountPemilik);
            userProfile1.setFullname(faker.name().fullName());
            userProfile1.setAddress(faker.address().fullAddress());
            userProfile1.setGender(EnumGender.MALE);
            userProfileRepository.save(userProfile1);

            UserBank userBank = new UserBank();
            userBank.setBankName("BCA");
            userBank.setAccountName(userProfile1.getFullname());
            userBank.setAccountNumber(faker.finance().creditCard());
            userBank.setAccount(accountPemilik);
            userBankRepo.save(userBank);

            UserValidation userValidation = new UserValidation();
            userValidation.setIdCardUrl("url");
            userValidation.setType(EnumIdCardType.KTP);
            userValidation.setAccount(accountPemilik);
            userValidationRepo.save(userValidation);

            Kost kost = new Kost();
            kost.setOwner(accountPemilik);
            kost.setAddress(faker.address().streetAddress());
            kost.setCity(faker.address().city());
            kost.setDistrict(faker.address().cityName());
            kost.setProvince(faker.address().city());

            Set<KostPaymentScheme> paymentSchemesSet = new HashSet<>();
            List<KostPaymentScheme> paymentSchemeList = kostPaymentSchemeRepository.findAll();

            paymentSchemeList.forEach(paymentScheme -> {
                paymentSchemesSet.add(paymentScheme);
            });

            kost.setKostPaymentScheme(paymentSchemesSet);
            kost.setKostName("Kost " + userProfile1.getFullname());
            kost.setOwner(accountPemilik);
            kost.setKostType(EnumKostType.PUTRA);

            for (int j=0;j<5;j++){
                Optional<KostRule> kostRule = kostRuleRepo.findById((long) j);

                if (kostRule.isPresent()){
                    kost.getKostRule().add(kostRule.get());
                }
            }

            kostRepository.save(kost);

            for (int j = 0; j < 2; j++) {
                RoomKost roomKost = new RoomKost();
                roomKost.setKost(kost);
                roomKost.setName("kamar"+faker.superhero().name());
                roomKost.setLabel("KOST_TERBARU");
                roomKost.setIsAvailable(true);
                roomKost.setQuantity(2);
                roomKost.setIndoorBathroom(true);
                roomKost.setLength(2.0);
                roomKost.setWidth(4.0);
                roomKost.setMaxPerson(2);
                roomKost.setPrice(600000.0);
                roomKost.setAvailableRoom(2);
                roomKost.getImageUrl().add("https://"+roomKost.getName()+"image_url.com");
                roomKost.getImageUrl().add("https://"+roomKost.getName()+"image_url.com");
                roomKost.getImageUrl().add("https://"+roomKost.getName()+"image_url.com");
                roomKostRepository.save(roomKost);





                for (int k = 0; k < 5; k++) {
                    Optional<RoomFacility> roomFacility =roomFacilityRepo.findById((long) k);

                    if (roomFacility.isPresent()){
                        roomKost.getRoomFacilitiesId().add(roomFacility.get());

                    }
                }

                for (int k = 0; k < 3; k++) {
                    Faker faker1 = new Faker(new Random(24));
                    AdditionalRoomFacility additionalRoomFacility = new AdditionalRoomFacility();
                    additionalRoomFacility.setPrice(20000.0);
                    additionalRoomFacility.setName(faker1.superhero().power());
                    additionalRoomFacility.setRoomKost(roomKost);
                    additionalRoomFacilityRepo.save(additionalRoomFacility);
                }

                roomKostRepository.save(roomKost);
                kost.getRoomKosts().add(roomKost);

            }

        }

    }

    private void insertToAccountTablePencari(){
        Long randomId = (long)random.nextInt((5 - 1) + 1) + 1;

        Role role1 = roleRepository.findByName(EnumRole.ROLE_USER_PENCARI).get();
        Set<Role> roles1 = new HashSet<>();
        roles1.add(role1);

        for (int i=0;i<1;i++){
            Faker faker = new Faker( new Locale("en-US"),new Random(100));
            Integer r = random.nextInt();
            String email = faker.bothify(faker.name().lastName()+".????"+r+"@pencari.com");
            Optional<Account> checkAccount = accountRepository.findByEmail(email);

            if (checkAccount.isPresent()){
                continue;
            }
            Account accountPencari = new Account();
            accountPencari.setEmail(email);
            accountPencari.setPassword(passwordEncoder.encode(defaultPassword));
            accountPencari.setPhone(faker.phoneNumber().phoneNumber());
            accountPencari.setRoles(roles1);
            accountRepository.save(accountPencari);

            UserProfile userProfile1 = new UserProfile();
            userProfile1.setAccount(accountPencari);
            userProfile1.setFullname(faker.name().fullName());
            userProfile1.setAddress(faker.address().fullAddress());
            userProfile1.setGender(EnumGender.MALE);
            userProfileRepository.save(userProfile1);

            UserBank userBank = new UserBank();
            userBank.setBankName("BCA");
            userBank.setAccountName(userProfile1.getFullname());
            userBank.setAccountNumber(faker.finance().creditCard());
            userBank.setAccount(accountPencari);
            userBankRepo.save(userBank);

            UserValidation userValidation = new UserValidation();
            userValidation.setIdCardUrl("url");
            userValidation.setType(EnumIdCardType.KTP);
            userValidation.setAccount(accountPencari);
            userValidationRepo.save(userValidation);

            accountRepository.save(accountPencari);

            faker = null;
        }

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

    private void insertToKostPaymentScheme(){
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

    private void insertToRoomFacility(){
        RoomFacility roomFacility1 = new RoomFacility();
        roomFacility1.setFacilityName("AC");
        roomFacility1.setType("FASILITAS_KAMAR");
        roomFacilityRepo.save(roomFacility1);

        RoomFacility roomFacility2 = new RoomFacility();
        roomFacility2.setFacilityName("TV");
        roomFacility2.setType("FASILITAS_KAMAR");
        roomFacilityRepo.save(roomFacility2);

        RoomFacility roomFacility3 = new RoomFacility();
        roomFacility3.setFacilityName("LEMARI");
        roomFacility3.setType("FASILITAS_KAMAR");
        roomFacilityRepo.save(roomFacility3);

        RoomFacility roomFacility4 = new RoomFacility();
        roomFacility4.setFacilityName("SHOWER");
        roomFacility4.setType("FASILITAS_KAMAR_MANDI");
        roomFacilityRepo.save(roomFacility4);

        RoomFacility roomFacility5 = new RoomFacility();
        roomFacility5.setFacilityName("CERMIN");
        roomFacility5.setType("FASILITAS_KAMAR_MANDI");
        roomFacilityRepo.save(roomFacility5);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Account> superuser = accountRepository.findByEmail("super@admin.com");

        if (!superuser.isPresent()){
            insertToRoomFacility();
            insertToRoleTable();
            insertToKostRuleTable();
            insertToKostPaymentScheme();
            insertToAccountTableSuperadmin();
            insertToAccountTablePemilik();
            insertToAccountTablePencari();
        }
    }
}