package com.kostserver.service.impl;

import com.kostserver.model.Account;
import com.kostserver.model.ConfirmationToken;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.ConfirmationTokenRepository;
import com.kostserver.service.OtpService;
import com.kostserver.utils.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailSender emailSender;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public String confirmOtp(String token) {

        ConfirmationToken ctoken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(()->
                        new IllegalStateException("Invalid token"));

        if (ctoken.getConfirmedAt()!=null){
            throw new IllegalStateException("Token already used");
        }

        LocalDateTime expiredAt = ctoken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token already expired");
        }

        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());

        String getEmail = ctoken.getAccount().getEmail();

        accountRepository.enableVerified(getEmail);

        return "success";
    }

    @Override
    public String generateToken() {
        Random random = new Random();
        int token = 10000 + random.nextInt(90000);
        return String.valueOf(token);
    }

    @Override
    public void sentOtp(Account account) {

        String token = generateToken();

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        confirmationToken.setAccount(account);

        confirmationTokenRepository.save(confirmationToken);

        emailSender.sendAsync(
                account.getEmail(),
                "OTP Verification token",
                "Verification token : "+token
        );



    }
}
