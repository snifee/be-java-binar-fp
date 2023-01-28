package com.kostserver.service;

import com.kostserver.dto.request.UpdateBankAccountDto;
import com.kostserver.dto.request.UpdateUserProfileDto;
import com.kostserver.dto.request.UserVerificationDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    Map updateUserBank(UpdateBankAccountDto request) throws Exception;

    Map updateUserVerification(UserVerificationDto request) throws Exception;

    Map getCurrentUser()throws Exception;

    Map updateUserProfile(UpdateUserProfileDto request) throws Exception;

}
