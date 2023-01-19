package com.kostserver.service.impl;

import com.kostserver.dto.LoginRequestDto;
import com.kostserver.model.entity.Account;
import com.kostserver.model.response.UserDetailsRespond;
import com.kostserver.repository.AccountRepository;
import com.kostserver.service.LoginService;
import com.kostserver.service.auth.AccountService;
import com.kostserver.utils.auth.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map login(LoginRequestDto requestDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();

        try{
            Optional<Account> accountExist =  accountRepository.findByEmail(requestDto.getEmail());

            if (!accountExist.isPresent()){
                throw new UsernameNotFoundException("Email not registered");
            }

            if (!bCryptPasswordEncoder.matches(requestDto.getPassword(),accountExist.get().getPassword())){
                throw new UsernameNotFoundException("Password wrong");
            }

            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(),requestDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(auth);

            UserDetails userDetails = accountService.loadUserByUsername(requestDto.getEmail());

            UserDetailsRespond userDetailsRespond = new UserDetailsRespond(accountExist.get(), accountExist.get().getUserProfile());

            String jwtToken = jwtUtils.generateToken(userDetails);

            data.put("access_token",jwtToken);
            data.put("user_details",userDetailsRespond);

            response.put("status", HttpStatus.OK);
            response.put("message","login");
            response.put("data",data);
        }catch (Exception e){
            response.put("status",HttpStatus.UNAUTHORIZED);
            response.put("message",e.getMessage());
        }

        return response;
    }
}
