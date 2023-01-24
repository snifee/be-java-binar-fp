package com.kostserver.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.request.RoomDto;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.RoomKost;
import com.kostserver.repository.RoomKostRepository;
import com.kostserver.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomKostRepository roomKostRepository;

    @Override
    public RoomKost addRoom (RoomDto request, MultipartFile[] file) {
        RoomKost room = new RoomKost();
        room.setName(request.getName());

        String[] image = request.getImages();
        if (!(image == null || image.length == 0 || image.toString().isEmpty())) {
            if (image[0].contains("https://res.cloudinary.com/kosthub/image/upload") &&
                    image[1].contains("https://res.cloudinary.com/kosthub/image/upload")) {
                String url1 = image[0].substring(0,47);
                String url2 = image[0].substring(47, image[0].length());
                String url3 = image[1].substring(0,47);
                String url4 = image[1].substring(47, image[1].length());
                String resolusi = "/w_500,h_500/";
                String newUrl1 = url1+resolusi+url2;
                String newUrl2 = url3+resolusi+url4;
                String[] imageRoom = new String[2];
                imageRoom[0] = newUrl1;
                imageRoom[1] = newUrl2;
                room.setImages(imageRoom);
            } else {
                room.setImages(request.getImages());
            }
        }

        for (int i=0; i<file.length; i++) {
            String contentType = file[i].getContentType();
            if (!(contentType.equals("image/png")
                    || contentType.equals("image/jpg")
                    || contentType.equals("image/jpeg"))) {
            }
            if (file.length <= 2) {
                try {
                    File converter = new File(file[i].getOriginalFilename());
                    FileOutputStream fos = new FileOutputStream(converter);
                    fos.write(file[i].getBytes());
                    fos.close();

                    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                            "cloud_name", "kosthub",
                            "api_key", "618124265592915",
                            "api_secret", "tjgZHec24_bcZZFCeVSd0ysL2qs"));
                    Map imgMap = cloudinary.uploader().upload(converter, ObjectUtils.emptyMap());
                    return (RoomKost) imgMap.get("url");
                } catch (IOException ioe) {
                }
            }
        }

        room.setQuantity(request.getQuantity());
        room.setPrice(request.getPrice());
        room.setLength(request.getLength());
        room.setWidth(request.getWidth());
        room.setMaxPerson(request.getMaxPerson());
        room.setIndoorBathroom(request.getIndoorBathroom());
        room.setBathroomFacilitiesId(request.getBathroomFacilitiesId());
        room.setBedroomFacilitiesId(request.getBedroomFacilitiesId());
        room.setAdditionalRoomFacilities(request.getAdditionalRoomFacilities());
        room.setCreatedDate(new Date());
        return roomKostRepository.save(room);
    }

    @Override
    public RoomKost updateRoom(Long id, RoomDto request) {
        RoomKost room = roomKostRepository.findById(id).get();
        if (room == null) {
            System.out.println("Room Id doesn't exist");
        } else
            room.setName(request.getName());
            room.setImages(request.getImages());
            room.setQuantity(request.getQuantity());
            room.setPrice(request.getPrice());
            room.setLength(request.getLength());
            room.setWidth(request.getWidth());
            room.setMaxPerson(request.getMaxPerson());
            room.setIndoorBathroom(request.getIndoorBathroom());
            room.setBathroomFacilitiesId(request.getBathroomFacilitiesId());
            room.setBedroomFacilitiesId(request.getBedroomFacilitiesId());
            room.setAdditionalRoomFacilities(request.getAdditionalRoomFacilities());
            room.setUpdatedDate(new Date());
            return roomKostRepository.save(room);
    }

    @Override
    public RoomKost getRoomById(Long id) {
        return roomKostRepository.findById(id).get();
    }

    @Override
    public Boolean delete(Long id) {
        Optional<RoomKost> room = roomKostRepository.findById(id);
        if (room.isPresent()) {
            RoomKost room1 = room.get();
            room1.setDeleted(true);
            roomKostRepository.save(room1);
            return true;
        }
        return false;
    }

    @Override
    public RoomKost getOwnerById(Long id, String name, String phone, Date createdDate) {
//        RoomKost roomOwner = roomKostRepository.getOwner(id, name, phone, createdDate);
//        return roomOwner;

        Optional<RoomKost> room = roomKostRepository.findById(id);
        if (room.isPresent()) {
            RoomKost account = new RoomKost();
            Account owner = account.getOwner();
            return getOwnerById(owner.getId(), owner.getName(), owner.getPhone(), owner.getCreatedDate());
        }
        return null;
    }

    @Override
    public RoomKost getRoomDetailsById(Long id) {
//        RoomKost room = roomKostRepository.getRoomDetailsById(id);
//        return room;
        return null;
    }

    @Override
    public List<RoomKost> findByKeyword(String keyword, int pageNo, int pageSize, int minPrice, int maxPrice, int rating) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<RoomKost> roomList = roomKostRepository.search(keyword.toLowerCase(Locale.ROOT),pageable,minPrice,maxPrice,rating).getContent();
        return roomList;
    }
}
