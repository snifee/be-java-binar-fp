package com.kostserver.service.impl;

import com.kostserver.dto.RegisterRequestDto;
import com.kostserver.model.Account;
import com.kostserver.model.ConfirmationToken;
import com.kostserver.model.EnumRole;
import com.kostserver.model.Role;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.ConfirmationTokenRepository;
import com.kostserver.repository.RoleRepository;
import com.kostserver.service.OtpService;
import com.kostserver.service.RegisterService;
import com.kostserver.service.auth.AccountService;
import com.kostserver.utils.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private OtpService otpService;

    @Autowired
    private AccountService accountService;

    @Override
    @Transactional
    public Map register(RegisterRequestDto request, EnumRole roleRq) {
        Map<String, Object> response = new HashMap<>();

        try{
            Optional<Account> accountExist =  accountRepository.findByEmail(request.getEmail());

            if (accountExist.isPresent()){

                if (!accountExist.get().getVerified()){
                    otpService.sentOtp(accountExist.get());
                    throw new IllegalStateException("Need verification, confirmation code sent");
                }

                throw new IllegalStateException("Email Already Taken");
            }

            Role role = roleRepository.findByName(roleRq).get();
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);

            Account account = new Account();
            account.setUsername(request.getEmail());
            account.setEmail(request.getEmail());
            account.setPhoneNumber(request.getPhone());
            account.setVerified(false);
            account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            account.setRoles(roleSet);

            accountRepository.save(account);
            otpService.sentOtp(account);

            response.put("status","success");
            response.put("message","Please check email for confirmation code");

        }catch(Exception e){
            log.info(e.getMessage());
            response.put("status","failed");
            response.put("message",e.getMessage());
        }



        return response;

    }
}
