package com.kostserver.service.auth;

import com.kostserver.dto.LoginRequestDto;

import java.util.Map;

public interface LoginService {
    Map login(LoginRequestDto requestDto);
}
