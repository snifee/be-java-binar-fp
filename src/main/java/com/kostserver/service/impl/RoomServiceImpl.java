package com.kostserver.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kostserver.dto.RoomDto;
import com.kostserver.dto.request.AddRatingRequest;
import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.Rating;
import com.kostserver.model.entity.RoomKost;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.RatingRepository;
import com.kostserver.repository.RoomKostRepository;
import com.kostserver.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomKostRepository roomKostRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RatingRepository ratingRepository;

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

    @Override
    public Map addRating(AddRatingRequest request) throws Exception {
        Rating rating = new Rating();
        rating.setRating(request.getRating());
        rating.setUlasan(request.getReview());
        rating.setAnonym(request.getAnonym());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> account = accountRepository.findByEmail(email);
        rating.setAccount(account.get());

        Optional<RoomKost> room = roomKostRepository.findById(request.getRoom_id());
        rating.setRoomKost(room.get());

        ratingRepository.save(rating);

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", HttpStatus.CREATED);
        response.put("message", "Rating Created");

        return response;
    }

    @Override
    public Map getRating(Long id, int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page - 1, size);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.OK);
        response.put("message", "Room Rating");
        response.put("data", roomKostRepository.getRating(id, pageable));

        return response;
    }

}
