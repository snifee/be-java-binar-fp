package com.kostserver.service;

import com.kostserver.dto.request.RoomDto;
import com.kostserver.model.entity.RoomKost;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface RoomService {

    RoomKost addRoom (RoomDto request) throws Exception;
    RoomKost updateRoom (Long id, RoomDto request) throws Exception;
//    Map getRoomById (Long id) throws Exception;
//    Boolean delete (Long id);
//    RoomKost getOwnerById (Long id, String name, String phone, Date createdDate);
    Map getRoomDetailsById (Long id) throws Exception;
//    List<RoomKost> findByKeyword (String keyword, int pageNo, int pageSize, int minPrice,int maxPrice, int rating);

}
