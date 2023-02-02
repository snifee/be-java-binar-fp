package com.kostserver.service;

import com.kostserver.dto.SearchRoomDto;
import com.kostserver.dto.request.RoomDto;
import com.kostserver.model.entity.RoomKost;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoomService {

    RoomKost addRoom (RoomDto request) throws Exception;
    RoomKost updateRoom (Long id, RoomDto request) throws Exception;

    Map getRoomDetailsById (Long id) throws Exception;

    Map getOwnerContact (Long roomId) throws Exception;

    List<SearchRoomDto> searchRoom(String keyword, String label, String type, Double minPrice, Double maxPrice, int size);
}
