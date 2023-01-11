package com.kostserver.utils;

import com.kostserver.model.Account;
import com.kostserver.model.EnumRole;
import com.kostserver.model.Role;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Service
public class DatabaseSeeder implements ApplicationRunner {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    String[] emails = {"admin@mail.com", "user1@mail.com", "user2@mail.com"};
    EnumRole[] roles = {EnumRole.ROLE_USER_PENYEDIA,EnumRole.ROLE_SUPERUSER};
    String defaultPassword = "password";

    private void insertToAccountTable(){

        Arrays.asList(emails).forEach((email)->{
            boolean accExist = accountRepository.findByEmail(email).isPresent();

            if (!accExist){
                Account account = new Account();
                account.setEmail(email);
                account.setPassword(passwordEncoder.encode(defaultPassword));
                account.setPhoneNumber("081234567");
                account.setUsername(email);
                account.setVerified(true);
                Set<Role> roleSet = new HashSet<>();

                if (email.contains("user")){
                    Role role = roleRepository.findByName(roles[0]).get();
                    roleSet.add(role);
                }else {
                    Role role = roleRepository.findByName(roles[1]).get();
                    roleSet.add(role);
                }
                account.setRoles(roleSet);

                accountRepository.save(account);
            }
        });
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        insertToRoleTable();
        insertToAccountTable();
    }
}
