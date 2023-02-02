package com.kostserver.service.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.model.entity.*;
import com.kostserver.repository.*;
import com.kostserver.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.kostserver.dto.request.RoomDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;


@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomKostRepository roomKostRepository;

    @Autowired
    private KostRepository kostRepository;

    @Autowired
    private RoomFacilityRepo roomFacilityRepo;

    @Autowired
    private AdditionalRoomFacilityRepo additionalRoomFacilityRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomKost addRoom (RoomDto request) throws Exception{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Kost> kost = kostRepository.findById(request.getKost_id());

        if (kost.isEmpty()){
            throw new IllegalStateException("kost with id="+request.getKost_id()+" not found");
        }

        if (!email.equals(kost.get().getOwner().getEmail())){
            throw new IllegalStateException("not allowed to add room in this kost");
        }

        RoomKost room = new RoomKost();
        room.setName(request.getName());
        room.setOwner(kost.get().getOwner());

        room.setKost(kost.get());

        if (!request.getImages().isEmpty()){
            request.getImages().forEach(image->{
                if (image.contains("data:image/jpeg;base64") && image!=null){

                    try {
                        Map resUrl = null;
                        resUrl = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                        room.getImageUrl().add(String.valueOf(resUrl.get("url")));
                    } catch (IOException e) {
                        log.info("image faild to saved");
                    }
                }
            });
        }

        Set<RoomFacility> roomFacilities = new HashSet<>();

        if (!request.getBathroom_facilities().isEmpty()){
            request.getBathroom_facilities().forEach(roomFacility -> {
                Optional<RoomFacility> facility = roomFacilityRepo.findById(roomFacility.getId());

                facility.ifPresent(roomFacilities::add);
            });
        }

        if (!request.getBedroom_facilities().isEmpty()){
            request.getBedroom_facilities().forEach(roomFacility -> {
                Optional<RoomFacility> facility = roomFacilityRepo.findById(roomFacility.getId());

                facility.ifPresent(roomFacilities::add);
            });
        }

        if (!roomFacilities.isEmpty()){
            room.setRoomFacilitiesId(roomFacilities);
        }

        if (!request.getAddons_facilities().isEmpty()){
            request.getAddons_facilities().forEach(additionalRoomFacility -> {
                AdditionalRoomFacility facility = additionalRoomFacilityRepo.save(additionalRoomFacility);
                facility.setRoomKost(room);
                room.getAdditionalRoomFacilities().add(facility);
            });
        }

        room.setLabel("KOST_TERBARU");
        room.setQuantity(request.getQuantity());
        room.setAvailableRoom(request.getQuantity());
        room.setPrice(request.getPrice());
        room.setLength(request.getLength());
        room.setWidth(request.getWidth());
        room.setMaxPerson(request.getMax_person());
        room.setIndoorBathroom(request.getIndoor_bathroom());
//        room.setAdditionalRoomFacilities(request.getAddons_facilities());
        return roomKostRepository.save(room);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomKost updateRoom(Long id, RoomDto request) throws Exception{
        RoomKost room = roomKostRepository.findById(id).get();
        if (room==null) {
            throw new IllegalStateException("Room Id doesn't exist");
        }
        Set<RoomFacility> facilities = new HashSet<>();
        if (!request.getBathroom_facilities().isEmpty()){
            request.getBathroom_facilities().stream().forEach(roomFacility -> {
                Optional<RoomFacility> facility = roomFacilityRepo.findById(roomFacility.getId());

                facility.ifPresent(facilities::add);
            });
        }

        if (!request.getBedroom_facilities().isEmpty()){
            request.getBedroom_facilities().stream().forEach(roomFacility -> {
                Optional<RoomFacility> facility = roomFacilityRepo.findById(roomFacility.getId());

                facility.ifPresent(facilities::add);
            });
        }

        if(!facilities.isEmpty()){
            room.setRoomFacilitiesId(facilities);
        }

        if (!request.getAddons_facilities().isEmpty()){
            Set<AdditionalRoomFacility> AddFacilities = new HashSet<>();
            request.getAddons_facilities().stream().forEach(additionalRoomFacility -> {
                additionalRoomFacility.setRoomKost(room);
                AdditionalRoomFacility facility = additionalRoomFacilityRepo.save(additionalRoomFacility);
                AddFacilities.add(facility);
            });
            room.setAdditionalRoomFacilities(AddFacilities);
        }

        if (!request.getImages().isEmpty()){
            List<String> images = new ArrayList<>();
            request.getImages().forEach(image->{
                if (image.contains("data:image/jpeg;base64") && image!=null){

                    try {
                        Map resUrl = null;
                        resUrl = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                        images.add(String.valueOf(resUrl.get("url")));
                    } catch (IOException e) {
                        log.info("image faild to saved");
                    }
                }
            });

            room.setImageUrl(images);
        }


        room.setName(request.getName());
        room.setQuantity(request.getQuantity());
        room.setPrice(request.getPrice());
        room.setLength(request.getLength());
        room.setWidth(request.getWidth());
        room.setMaxPerson(request.getMax_person());
        room.setIndoorBathroom(request.getIndoor_bathroom());
        return roomKostRepository.save(room);
    }

    @Transactional(readOnly = true)
    @Override
    public Map getRoomDetailsById(Long id) throws Exception{
        Map<String,Object> data = new LinkedHashMap<>();
        Optional<RoomKost> checkRoom = roomKostRepository.findById(id);

        if (checkRoom.isEmpty()){
            throw new IllegalStateException("room not found");
        }

        RoomKost room = checkRoom.get();
        Kost kost = room.getKost();

        data.put("id",room.getId());
        data.put("name",room.getName());
        data.put("type",room.getKost().getKostType().toString());

        Integer totalRating = room.getRating().stream()
                .map(r -> r.getRating())
                .reduce(0,Integer::sum);

        Double avgRating = null;

        if (room.getRating().size() != 0){
            avgRating = (double) (totalRating/room.getRating().size());
        }

        data.put("rating",avgRating);

        data.put("label",room.getLabel());
        data.put("description",kost.getDescription());
        data.put("max_person",room.getMaxPerson());
        data.put("price",room.getPrice());
        data.put("long",room.getKost().getLongitude());
        data.put("lat",room.getKost().getLongitude());
        data.put("address",room.getKost().getAddress());
        data.put("province",room.getKost().getProvince());
        data.put("city",room.getKost().getCity());
        data.put("district",room.getKost().getDistrict());
        data.put("note",room.getKost().getAddressNote());
        data.put("images",room.getImageUrl());

        List<Map<String, Object>> facilities = new ArrayList<>();

        room.getRoomFacilitiesId().forEach(facility -> {
            Map<String,Object> f = new LinkedHashMap<>();

            f.put("id",facility.getId());
            f.put("name",facility.getFacilityName());

            facilities.add(f);
        });

        data.put("facilities",facilities);

        List<Map<String, Object>> rules = new ArrayList<>();

        kost.getKostRule().forEach(kostRule -> {
            Map<String,Object> r = new LinkedHashMap<>();

            r.put("id",kostRule.getId());
            r.put("name",kostRule.getRule());

            rules.add(r);
        });
        data.put("rules",rules);


        List<Map> anotherRoomList = new ArrayList<>();

        kost.getRoomKosts()
                .stream().forEach(anotherRoom ->{
                    if (anotherRoom.getId() != room.getId()){
                        Map<String, Object> roomMap = new LinkedHashMap<>();
                        roomMap.put("id",anotherRoom.getId());
                        roomMap.put("name",anotherRoom.getName());
                        roomMap.put("price",anotherRoom.getPrice());

                        if (anotherRoom.getImageUrl().isEmpty()){
                            roomMap.put("thumbnail",null);
                        }else {
                            roomMap.put("thumbnail",anotherRoom.getImageUrl().get(0));
                        }

                        Map<String, Object> location = new LinkedHashMap<>();
                        location.put("city",kost.getCity());
                        location.put("district",kost.getDistrict());
                        roomMap.put("location",location);

                        Integer total = anotherRoom.getRating().stream()
                                .map(r -> r.getRating())
                                .reduce(0,Integer::sum);

                        Double avgRating1 = null;

                        if (room.getRating().size() != 0){
                            avgRating1 = (double) (totalRating/anotherRoom.getRating().size());
                        }

                        roomMap.put("rating",avgRating1);

                        anotherRoomList.add(roomMap);
                    }
                });
        data.put("another_room",anotherRoomList);

        List<Map<String, Object>> paymentSchemes = new ArrayList<>();

        kost.getKostPaymentScheme().forEach(facility -> {
            Map<String,Object> p = new LinkedHashMap<>();

            p.put("id",facility.getId());
            p.put("name",facility.getPayment_scheme());

            paymentSchemes.add(p);
        });

        data.put("payment_scheme",paymentSchemes);

        data.put("addons_facilities",room.getAdditionalRoomFacilities());


        return data;
    }

    @Override
    public Map getOwnerContact(Long roomId) throws Exception {

        Optional<RoomKost> room = roomKostRepository.findById(roomId);

        if (room.isEmpty()){
            throw new IllegalStateException("Room not found");
        }

        Account owner = room.get().getKost().getOwner();

        Map<String ,Object> data = new LinkedHashMap<>();

        data.put("id",owner.getId());

        data.put("name",owner.getUserProfile().getFullname());

        data.put("phone",owner.getPhone());

        data.put("created_at",owner.getCreatedDate());

        return data;
    }


}
