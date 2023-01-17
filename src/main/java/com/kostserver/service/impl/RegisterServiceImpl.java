package com.kostserver.service.impl;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.kostserver.dto.RegisterRequestDto;
import com.kostserver.model.entity.*;
import com.kostserver.model.response.UserDetailsRespond;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.ConfirmationTokenRepository;
import com.kostserver.repository.RoleRepository;
import com.kostserver.repository.UserProfileRepository;
import com.kostserver.service.OtpService;
import com.kostserver.service.RegisterService;
import com.kostserver.service.auth.AccountService;
import com.kostserver.utils.EmailSender;
import com.kostserver.utils.PhoneNumberValidator;
import com.kostserver.utils.auth.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
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
    private JwtUtils jwtUtils;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserProfileRepository userProfileRepository;


    @Override
    @Transactional
    public Map register(RegisterRequestDto request, EnumRole roleRq) {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String,Object> data = new HashMap<>();

        EmailValidator emailValidator = EmailValidator.getInstance();


        try{
//            if (!emailValidator.isValid(request.getEmail())){
//                throw new IllegalStateException("Invalid email");
//            }

            Optional<Account> accountExist =  accountRepository.findByEmail(request.getEmail());
            Account accountResponse = null;
            UserDetails userDetails = null;

            if (accountExist.isPresent()){

//                accountResponse = accountExist.get();

//                if (accountExist.get().getVerified()){
                throw new IllegalStateException("Email Already Taken");
//                }
//                else {
//                    otpService.sentOtp(accountExist.get());
//                    throw new IllegalStateException("Email Already Taken but account not verified");
//                }

            }else {
                Role role = roleRepository.findByName(roleRq).get();
                Set<Role> roleSet = new HashSet<>();
                roleSet.add(role);

                UserProfile userProfile = new UserProfile();

                Account account = new Account();
                account.setEmail(request.getEmail());
                account.setPhone(request.getPhone());
                account.setVerified(false);
                account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
                account.setRoles(roleSet);
                account.setUserProfileId(userProfile);

                userProfile.setAccountId(account);

                userProfileRepository.save(userProfile);
                accountRepository.save(account);
//                otpService.sentOtp(account);

                accountResponse = account;
            }

            userDetails = accountService.loadUserByUsername(request.getEmail());
//            UserProfile userProfile1 = accountResponse.getUserProfileId();

            UserDetailsRespond udr = new UserDetailsRespond(accountResponse,accountResponse.getUserProfileId());

            String jwt = jwtUtils.generateToken(userDetails);
            data.put("access_token",jwt);
            data.put("user_details",udr);

            response.put("status",HttpStatus.CREATED);
            response.put("message","Please check email for confirmation code");
            response.put("data",data);

        }catch(Exception e){
            log.info(e.getMessage());
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message",e.getMessage());
        }



        return response;

    }
}
