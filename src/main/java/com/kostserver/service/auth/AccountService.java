package com.kostserver.service.auth;

import com.kostserver.model.EnumRole;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.Role;
import com.kostserver.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

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
}
