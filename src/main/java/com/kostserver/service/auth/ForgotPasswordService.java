package com.kostserver.service.auth;

import java.util.Map;

import com.kostserver.dto.request.ForgotPasswordRequestDto;

public interface ForgotPasswordService {
    Map forgotPassword(ForgotPasswordRequestDto request);

}
