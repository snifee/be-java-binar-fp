package com.kostserver.service.impl;

import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.Role;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.RoleRepository;
import com.kostserver.service.GetUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class GetUserInfoImpl implements GetUserInfo {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Map userInfo(String email) {

        Optional<Account> account = accountRepository.findByEmail(email);
        Set<Role> roles = account.get().getRoles();
        Long id = account.get().getId();
        String accountEmail = account.get().getEmail();

        Map<String, Object> currentUser = new HashMap<>();

        return currentUser;
    }

}
