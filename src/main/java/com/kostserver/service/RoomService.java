package com.kostserver.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kostserver.dto.RoomDto;
import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.RoomKost;

@Service
public interface RoomService {
    List<RoomDto> searchRoom(String keyword, String label, String type, Double minPrice, Double maxPrice,
            int size);

}
