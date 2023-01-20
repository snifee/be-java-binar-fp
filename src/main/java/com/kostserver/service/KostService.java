package com.kostserver.service;

import com.kostserver.dto.AddKostDto;

import java.util.Map;

public interface KostService {
    Map saveKost(AddKostDto request) throws Exception;
}
