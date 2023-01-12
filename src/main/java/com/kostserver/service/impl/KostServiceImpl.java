package com.kostserver.service.impl;

import com.kostserver.dto.KostDetailDto;
import com.kostserver.dto.RoomKostDto;
import com.kostserver.model.entity.*;
import com.kostserver.model.entity.RoomPriceCategory;
import com.kostserver.model.*;
import com.kostserver.model.request.CreateKostRequest;
import com.kostserver.repository.*;
import com.kostserver.service.KostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KostServiceImpl implements KostService {

    @Autowired
    KostRepository kostRepository;
    @Autowired
    RoomFacilityRepository roomFacilityRepository;
    @Autowired
    KostThumbnailRepository kostThumbnailRepository;
    @Autowired
    RoomPriceCategoryRepository roomPriceCategoryRepository;
    @Autowired
    RoomKostRepository roomKostRepository;
    @Autowired
    RoomImageRepository roomImageRepository;
    @Autowired
    KostLocationRepository kostLocationRepository;
    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Override
    public CreateKostRequest insert(RoomKostDto roomKostDto) {
        RoomKost roomKost = new RoomKost();
        roomKost.setRoomNumber(roomKostDto.getRoomNumber());
        roomKost.setDescription(roomKostDto.getDescription());
        roomKost.setCapacity(roomKostDto.getCapacity());
        roomKost.setPricePerCategory(roomKostDto.getPricePerCategory());
        roomKost.setIsAvailable(roomKostDto.getIsAvailable());

        RoomImage roomImage = new RoomImage();
        if (!(roomKostDto.getImageUrl() == null || roomKostDto.getImageUrl().isEmpty())) {
            if (roomKostDto.getImageUrl().get(0).contains("https://res.cloudinary.com/kosthub/image/upload") &&
                    roomKostDto.getImageUrl().get(2).contains("https://res.cloudinary.com/kosthub/image/upload") &&
                    roomKostDto.getImageUrl().get(3).contains("https://res.cloudinary.com/kosthub/image/upload") &&
                    roomKostDto.getImageUrl().get(4).contains("https://res.cloudinary.com/kosthub/image/upload")) {
                List<String> imageUrl = roomKostDto.getImageUrl();
                String url1 = imageUrl.get(0).substring(0,47);
                String url2 = imageUrl.get(0).substring(47, imageUrl.get(0).length());
                String url3 = imageUrl.get(1).substring(0,47);
                String url4 = imageUrl.get(1).substring(47, imageUrl.get(1).length());
                String url5 = imageUrl.get(2).substring(0,47);
                String url6 = imageUrl.get(2).substring(47, imageUrl.get(2).length());
                String url7 = imageUrl.get(3).substring(0,47);
                String url8 = imageUrl.get(3).substring(47, imageUrl.get(3).length());
                String resolusi = "/w_500,h_500/";
                String newUrl1 = url1+resolusi+url2;
                String newUrl2 = url3+resolusi+url4;
                String newUrl3 = url5+resolusi+url6;
                String newUrl4 = url7+resolusi+url8;

                List<String> imageRoomKost = new ArrayList<>(3);
                imageRoomKost.add(newUrl1);
                imageRoomKost.add(newUrl2);
                imageRoomKost.add(newUrl3);
                imageRoomKost.add(newUrl4);
                roomImage.setImageUrl(imageRoomKost);
            }
            else {
                roomImage.setImageUrl(roomKostDto.getImageUrl());
            }
        }


        String priceCategory = roomKostDto.getPriceCategory();
        Optional<RoomPriceCategory> priceCategoryOptional = this.roomPriceCategoryRepository.findByPriceCategory(priceCategory);
        RoomPriceCategory roomPriceCategory;
        if(priceCategoryOptional.isPresent()) {
            roomPriceCategory = priceCategoryOptional.get();
        } else {
            RoomPriceCategory newPriceCategory = new RoomPriceCategory();
            newPriceCategory.setPriceCategory(priceCategory);
            roomPriceCategory = roomPriceCategoryRepository.save(newPriceCategory);
        }
        roomKost.setRoomPriceCategory(roomPriceCategory);

        String type = roomKostDto.getRoomType();
        Optional<RoomType> roomTypeOptional = this.roomTypeRepository.findByType(type);
        RoomType roomType;
        if(roomTypeOptional.isPresent()) {
            roomType = roomTypeOptional.get();
        } else {
            RoomType newRoomType = new RoomType();
            newRoomType.setType(type);
            roomType = roomTypeRepository.save(newRoomType);
        }
        roomKost.setRoomType(roomType);

        String facilityName = roomKostDto.getRoomFacility();
        Optional<RoomFacility> facilityOptional = this.roomFacilityRepository.findByFacilityName(facilityName);
        RoomFacility roomFacility;
        if(facilityOptional.isPresent()) {
            roomFacility = facilityOptional.get();
        } else {
            RoomFacility newRoomFacility = new RoomFacility();
            newRoomFacility.setFacilityName(facilityName);
            roomFacility = roomFacilityRepository.save(newRoomFacility);
        }
        Set<RoomFacility> roomFacilities = new HashSet<>();
        roomFacilities.add(roomFacility);

        RoomKost roomKostInserted = roomKostRepository.save(roomKost);
        CreateKostRequest roomKostDtoInserted = new CreateKostRequest();
        roomKostDtoInserted.setId(roomKostInserted.getId());
        roomKostDtoInserted.setRoomNumber(roomKostInserted.getRoomNumber());
        roomKostDtoInserted.setCapacity(roomKostInserted.getCapacity());
        roomKostDtoInserted.setDescription(roomKostInserted.getDescription());
        roomKostDtoInserted.setPricePerCategory(roomKostInserted.getPricePerCategory());
        roomKostDtoInserted.setIsAvailable(roomKostInserted.getIsAvailable());
        roomKostDtoInserted.setRoomFacility(roomKostInserted.getRoomFacilities());
        roomKostDtoInserted.setRoomPriceCategory(roomKostInserted.getRoomPriceCategory());
        roomKostDtoInserted.setRoomType(roomKostInserted.getRoomType());
        roomKostDtoInserted.setRoomImage(roomKostInserted.getRoomImage());

        return roomKostDtoInserted;
    }

    @Override
    public KostDetailDto getById(Long id) {
        Optional<RoomKost> roomKostOptional = roomKostRepository.findById(id);
        if (roomKostOptional.isPresent()) {
            roomKostOptional.get();
            return KostDetailDto.of(roomKostOptional.get());
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        Optional<RoomKost> roomKost = roomKostRepository.findById(id);
        if (roomKost.isPresent()) {
            RoomKost roomKost1 = roomKost.get();
            roomKost1.setDeleted(true);
            roomKostRepository.save(roomKost1);
            return true;
        }
        return false;
    }
}
