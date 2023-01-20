package com.kostserver.service;

import com.kostserver.dto.UpdateBankAccountDto;
import com.kostserver.dto.UserVerificationDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    Map updateUserBank(UpdateBankAccountDto request) throws Exception;

    Map updateUserVerification(UserVerificationDto request) throws Exception;

}
