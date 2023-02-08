package com.kostserver.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kostserver.dto.request.ForgotPasswordRequestDto;
import com.kostserver.model.entity.Account;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.ConfirmationTokenRepository;
import com.kostserver.service.auth.ForgotPasswordService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Map forgotPassword(ForgotPasswordRequestDto request, String token) {
        Map<String, Object> response = new LinkedHashMap<>();
        Account account = null;
        if (request.getEmail() != null) {
            account = accountRepository.findByEmail(request.getEmail()).get();
        } else if (request.getPhone() != null) {
            account = accountRepository.findByPhone(request.getPhone()).get();
        }

        if (account != null) {
            Boolean isTokenExist = confirmationTokenRepository.existsByToken(token);
            if (isTokenExist) {
                account.setPassword(passwordEncoder.encode(request.getPassword()));
                accountRepository.save(account);
                response.put("status", HttpStatus.OK);
                response.put("message", "Password is changed");
            } else {
                response.put("status", HttpStatus.BAD_REQUEST);
                response.put("message", "Token is not valid");
            }
        } else {
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "Account is not found");
        }
        return response;
    }

}
