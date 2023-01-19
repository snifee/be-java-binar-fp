package com.kostserver.service;

import com.kostserver.dto.RegisterRequestDto;
import com.kostserver.model.EnumRole;

import java.util.Map;

public interface RegisterService {

    public Map register(RegisterRequestDto request, EnumRole role);

}
