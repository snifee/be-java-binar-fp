package com.kostserver.service.auth;

import com.kostserver.model.EnumRole;
import com.kostserver.model.entity.*;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.RoleRepository;
import com.kostserver.repository.UserBankRepo;
import com.kostserver.repository.UserProfileRepository;
import com.kostserver.repository.test.UserValidationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserBankRepo userBankRepo;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserValidationRepo userValidationRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account accountExist =  accountRepository.findByEmail(username).get();

        if (accountExist==null){
            throw new UsernameNotFoundException("User not found");
        }
        return new User(accountExist.getEmail(),
                        accountExist.getPassword(),
                        true,
                true,
                true,
                true, accountExist.getRoles().stream()
                .map(Role::getName)
                .map((EnumRole role) -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toSet()));
    }

    public Account processOAuthPostLogin(String email, Map<String,Object> userInfo){
        Optional<Account> account = accountRepository.findByEmail(email);

        if (account.isEmpty()){
            Role role = roleRepository.findByName(EnumRole.ROLE_USER_PEMILIK).get();
            Role role2 = roleRepository.findByName(EnumRole.ROLE_USER_PENCARI).get();
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            roleSet.add(role2);

            UserProfile userProfile = new UserProfile();
            userProfile.setPhotoUrl(String.valueOf(userInfo.get("picture")));
            userProfile.setFullname(String.valueOf(userInfo.get("name")));
            UserBank userBank = new UserBank();
            UserValidation userValidation= new UserValidation();

            Account newAccount = new Account();
            newAccount.setEmail(email);
            newAccount.setPhone(null);
            newAccount.setVerified(true);
            newAccount.setPassword(passwordEncoder.encode("password123"));
            newAccount.setRoles(roleSet);

            accountRepository.save(newAccount);

            userProfile.setAccount(newAccount);
            userBank.setAccount(newAccount);
            userValidation.setAccount(newAccount);

            userProfileRepository.save(userProfile);
            userBankRepo.save(userBank);
            userValidationRepo.save(userValidation);

            newAccount.setUserProfile(userProfile);
            newAccount.setUserValidation(userValidation);
            newAccount.setUserBank(userBank);

            accountRepository.save(newAccount);

            return newAccount;
        }
        return account.get();
    }
}
