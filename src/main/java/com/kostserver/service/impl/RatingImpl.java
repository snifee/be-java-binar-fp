package com.kostserver.service.impl;

import com.kostserver.dto.RatingDto;
import com.kostserver.model.Rating;
import com.kostserver.model.Room;
import com.kostserver.repository.RatingRepository;
import com.kostserver.repository.RoomRepository;
import com.kostserver.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RatingImpl implements RatingService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RatingRepository ratingRepository;

    @Override
    @Transactional
    public Map insert(RatingDto request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Rating rating = new Rating();
            rating.setRating(request.getRating());
            rating.setReviewText(request.getReviewText());

            Optional<Room> room = roomRepository.findById(request.getRoomId());

            if(room.isPresent()){
                rating.setRoom(room.get());
            }

            Rating data = ratingRepository.save(rating);

            response.put("status","success");
            response.put("data", data);
        }catch (Exception e){
            response.put("status","failed");
            response.put("message",e.getMessage());
        }
        return response;
    }
}
