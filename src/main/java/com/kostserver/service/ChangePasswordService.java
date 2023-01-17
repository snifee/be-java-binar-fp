package com.kostserver.service;

import com.kostserver.dto.ChangePasswordDto;

import java.util.Map;

public interface ChangePasswordService {
    Map changePassword(ChangePasswordDto request);
}
