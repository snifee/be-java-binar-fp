package com.kostserver.service;

import com.kostserver.dto.request.AddKostDto;
import com.kostserver.dto.request.UpdateKostDto;
import com.kostserver.model.entity.Kost;

import java.util.Map;

public interface KostService {
    Kost addKost(AddKostDto request) throws Exception;
    Kost updateKost(UpdateKostDto request) throws Exception;
}
