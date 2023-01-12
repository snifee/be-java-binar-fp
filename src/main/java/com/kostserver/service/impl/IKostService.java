//package com.kostserver.service.impl;
//
//import com.kostserver.dto.KostDetailDto;
//import com.kostserver.dto.RoomKostDto;
//import com.kostserver.model.entity.Kost;
//import com.kostserver.model.entity.RoomImage;
//import com.kostserver.model.entity.KostLocation;
//import com.kostserver.model.entity.RoomKost;
//import com.kostserver.model.request.CreateKostRequest;
//import com.kostserver.repository.*;
//import com.kostserver.service.KostService;
//import org.apache.commons.lang3.ArrayUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//
//@Service
//public class IKostService implements KostService {
//
//    @Autowired
//    KostRepository kostRepository;
//    @Autowired
//    RoomFacilityRepository roomFacilityRepository;
//    @Autowired
//    KostThumbnailRepository kostThumbnailRepository;
//    @Autowired
//    RoomPriceCategoryRepository roomPriceCategoryRepository;
//    @Autowired
//    RoomKostRepository roomKostRepository;
//    @Autowired
//    RoomImageRepository roomImageRepository;
//    @Autowired
//    KostLocationRepository kostLocationRepository;
//    @Autowired
//    RoomTypeRepository roomTypeRepository;
//
//    @Override
//    public CreateKostRequest insert(RoomKostDto roomKostDto) {
//        Kost kost = new Kost();
//        kost.setKostName(roomKostDto.getKostName());
//        kost.setPrice(roomKostDto.getPrice());
//
//        RoomImage roomImage = new RoomImage();
//        roomImage.setKostImageId(new KostImageId());
//        String url1 = roomKostDto.getUrl1();
//        String url2 = roomKostDto.getUrl2();
//        String url3 = roomKostDto.getUrl3();
//        String url4 = roomKostDto.getUrl4();
//
//        if (!(roomKostDto.getImageUrl() == null || ArrayUtils.isEmpty(roomKostDto.getImageUrl()))) {
//            if (url1.contains("https://res.cloudinary.com/kosthub/image/upload") &&
//                    url2.contains("https://res.cloudinary.com/kosthub/image/upload") &&
//                    url3.contains("https://res.cloudinary.com/kosthub/image/upload") &&
//                    url4.contains("https://res.cloudinary.com/kosthub/image/upload")) {
//
//                String link1 = url1;
//                String link2 = url2;
//                String link3 = url3;
//                String link4 = url4;
//                String sub1 = link1.substring(0, 47);
//                String sub2 = link1.substring(48, link1.length());
//                String sub3 = link2.substring(0, 47);
//                String sub4 = link2.substring(48, link2.length());
//                String sub5 = link3.substring(0, 47);
//                String sub6 = link3.substring(48, link3.length());
//                String sub7 = link4.substring(0, 47);
//                String sub8 = link4.substring(48, link4.length());
//                String resolusi = "/w_500,h_500/";
//                String newUrl1 = sub1 + resolusi + sub2;
//                String newUrl2 = sub3 + resolusi + sub4;
//                String newUrl3 = sub5 + resolusi + sub6;
//                String newUrl4 = sub7 + resolusi + sub8;
//
//                roomImage.setKostImageId(new KostImageId(newUrl1, newUrl2, newUrl3, newUrl4));
//            } else {
//                roomImage.setImageUrl(new String[]{url1, url2, url3, url4});
//            }
//        }
//
//        Kost kost1 = kostRepository.findById(kost.getId()).get();
//        KostType kostType = roomTypeRepository.findById(roomKostDto.getTypeId()).get();
//        kost1.setTypeId(kostType);
//
//        KostLocation kostLocation = kostLocationRepository.findById(roomKostDto.getLocationId()).get();
//        kost1.setLocationId(kostLocation);
//
//        Kost kostInserted = kostRepository.save(kost);
//        CreateKostRequest kostDtoInserted = new CreateKostRequest();
//        kostDtoInserted.setId(kostInserted.getId());
//        kostDtoInserted.setKostName(kostInserted.getKostName());
//        kostDtoInserted.setPrice(kostInserted.getPrice());
//        kostDtoInserted.setPrice(kostInserted.getPrice());
//        kostDtoInserted.setTypeId(kostInserted.getTypeId());
//        kostDtoInserted.setLocationId(kostInserted.getLocationId());
//        kostDtoInserted.setImageUrl(kostInserted.getImageId().getImageUrl());
//
//        return kostDtoInserted;
//    }
//
//    @Override
//    public KostDetailDto getById(Long id) {
//        Optional<RoomKost> kostOptional = roomKostRepository.findById(id);
//        if (kostOptional.isPresent()) {
//            kostOptional.get();
//            return KostDetailDto.of(kostOptional.get());
//        }
//        return null;
//    }
//
//    private Kost convertKostToKostDto (Kost kostCreated) {
//        //create new
//        if (kostCreated.getId() == null) {
//            Kost kostDtoCreated = new Kost();
//            kostDtoCreated.setPrice(kostCreated.getPrice());
//            kostDtoCreated.setQty(kostCreated.getQty());
//            kostDtoCreated.setName(kostCreated.getName());
//            kostDtoCreated.getBrand();
//            //productDtoCreated.getCategory();
//            return kostDtoCreated;
//
//        } else {
//            Optional<Kost> existingProductOptional = kostRepository.findById(kostCreated.getId());
//            Kost existingProduct = existingProductOptional.get();
//            Kost productDtoCreated = new Kost();
//            productDtoCreated.setId(existingProduct.getId());
//            productDtoCreated.setName(existingProduct.getName());
//            productDtoCreated.setQty(existingProduct.getQty());
//            productDtoCreated.setPrice(existingProduct.getPrice());
//
//            Brand brandDtoUpdate = new Brand();
//            brandDtoUpdate.setId(existingProduct.getBrand().getId());
//            brandDtoUpdate.setName(existingProduct.getBrand().getName());
//            productDtoCreated.setBrand(brandDtoUpdate);
//
//            Categories categoriesDtoUpdate = new Categories();
//            categoriesDtoUpdate.setCategoryId(existingProduct.getCategories().getCategoryId());
//            categoriesDtoUpdate.setName(existingProduct.getCategories().getName());
//            productDtoCreated.setCategories(categoriesDtoUpdate);
//            return productDtoCreated;
//        }
//    }
//
//
//
//    @Override
//    public Boolean delete(Long id) {
//        Optional<Kost> kost = kostRepository.findById(id);
//        if (kost.isPresent()) {
//            Kost kost1 = kost.get();
//            kost1.setDeleted(true);
//            kostRepository.save(kost1);
//            return true;
//        }
//        return false;
//    }
//}
