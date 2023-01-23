package com.kostserver.service;

import com.kostserver.dto.request.AddKostDto;
import com.kostserver.dto.request.UpdateKostDto;

import java.util.Map;

public interface KostService {
    Map addKost(AddKostDto request) throws Exception;
    Map updateKost(UpdateKostDto request) throws Exception;
}
