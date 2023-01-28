package com.kostserver.service;

import com.kostserver.dto.request.ChangePasswordDto;
import com.kostserver.model.response.Response;

import java.util.Map;

public interface ChangePasswordService {
    String changePassword(ChangePasswordDto request);
}
