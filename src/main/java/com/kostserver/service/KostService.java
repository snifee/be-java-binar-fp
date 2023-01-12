package com.kostserver.service;

import com.kostserver.dto.KostDetailDto;
import com.kostserver.dto.RoomKostDto;
import com.kostserver.model.request.CreateKostRequest;

public interface KostService {

    public CreateKostRequest insert (RoomKostDto roomKostDto);

    public KostDetailDto getById (Long id);

    Boolean delete (Long id);
}
