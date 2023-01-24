package com.kostserver.service;

import com.kostserver.dto.request.RoomDto;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.RoomKost;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface RoomService {

    RoomKost addRoom (RoomDto request, MultipartFile[] file);
    RoomKost updateRoom (Long id, RoomDto request);
    RoomKost getRoomById (Long id);
    Boolean delete (Long id);
    RoomKost getOwnerById (Long id, String name, String phone, Date createdDate);
    RoomKost getRoomDetailsById (Long id);
    List<RoomKost> findByKeyword (String keyword, int pageNo, int pageSize, int minPrice,int maxPrice, int rating);

}
