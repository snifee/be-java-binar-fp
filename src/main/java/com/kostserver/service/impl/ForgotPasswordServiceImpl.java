package com.kostserver.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public String forgotPassword(ForgotPasswordRequestDto request) throws Exception{
        Account account = null;
        if (request.getEmail() != null) {
            account = accountRepository.findByEmail(request.getEmail()).get();
        } else if (request.getPhone() != null) {
            account = accountRepository.findByPhone(request.getPhone()).get();
        }

        if (account != null) {
            List<String > confirmationTokenList = confirmationTokenRepository.listTokenByAccountId(account.getId());

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

            boolean isTokenExist = false;

            for (String ct : confirmationTokenList) {
                log.info(ct);
                log.info(request.getToken());
                if (bCryptPasswordEncoder.matches(request.getToken(), ct)) {
                    isTokenExist = true;
                }
            }

            if (isTokenExist) {
                account.setPassword(passwordEncoder.encode(request.getPassword()));
                accountRepository.save(account);

                return "password changed";
            } else {
                throw new IllegalStateException("invalid token");
            }
        } else {
            throw new IllegalStateException("Account is not found");
        }
    }

}
