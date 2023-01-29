package com.kostserver.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kostserver.dto.RoomDto;
import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.RoomKost;
import com.kostserver.repository.RoomKostRepository;
import com.kostserver.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomKostRepository roomKostRepository;

    @Override
    public List<RoomDto> searchRoom(String keyword, String label, String type,
            Double minPrice, Double maxPrice,
            int size) {
        Pageable pageable = PageRequest.of(0, size);
        EnumKostType kostType;
        if (type == null) {
            kostType = null;
        } else {
            kostType = EnumKostType.getTypeFromCode(type);
        }

        List<RoomDto> roomData = new ArrayList<>();
        roomData = roomKostRepository.searchRoom(keyword.toLowerCase(Locale.ROOT),
                label.toLowerCase(Locale.ROOT),
                kostType,
                minPrice,
                maxPrice,
                pageable);
        roomData.stream().forEach(room -> {
            RoomKost roomKost = roomKostRepository.getRoom(room.getId());
            if (room.getRating() == null) {
                room.setRating(0D);
            }
            if (!roomKost.getImageUrl().isEmpty()) {
                room.setThumbnail(roomKost.getImageUrl().get(0));
            }
        });
        return roomData;
    }

}
