package com.kostserver.service.auth;

import com.kostserver.dto.request.ForgotPasswordRequestDto;

public interface ForgotPasswordService {
    String forgotPassword(ForgotPasswordRequestDto request) throws Exception;

}
