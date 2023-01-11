package com.kostserver.service;

import com.kostserver.dto.LoginRequestDto;

import java.util.Map;

public interface LoginService {
    Map login(LoginRequestDto requestDto);
}
