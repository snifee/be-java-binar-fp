package com.kostserver.service;

import com.kostserver.dto.request.AddKostDto;

import java.util.Map;

public interface KostService {
    Map saveKost(AddKostDto request) throws Exception;
}
