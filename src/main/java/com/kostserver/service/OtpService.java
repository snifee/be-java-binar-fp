package com.kostserver.service;

import com.kostserver.model.entity.Account;

public interface OtpService {
    String confirmOtp(String token);

    String generateToken();

    void sentOtp(Account account);
}
