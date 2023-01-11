package com.kostserver.service.impl;

import com.kostserver.dto.LoginRequestDto;
import com.kostserver.model.Account;
import com.kostserver.repository.AccountRepository;
import com.kostserver.service.LoginService;
import com.kostserver.service.auth.AccountService;
import com.kostserver.utils.auth.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        Map<String, Object> response = new HashMap<>();

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

            String jwtToken = jwtUtils.generateToken(userDetails);

            response.put("status","success");
            response.put("access_token",jwtToken);
        }catch (Exception e){
            response.put("status","failed");
            response.put("message",e.getMessage());
        }

        return response;
    }
}
