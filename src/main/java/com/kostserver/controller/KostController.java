package com.kostserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostserver.dto.AddKostDto;
import com.kostserver.dto.AddKostRequestDto;
import com.kostserver.dto.LocationDto;
import com.kostserver.model.entity.KostPaymentScheme;
import com.kostserver.service.KostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import javax.xml.stream.Location;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/kost/")
@Slf4j
public class KostController {
    @Autowired
    private KostService kostService;

    @PostMapping("/addkost")
    ResponseEntity<Map> addkost(@ModelAttribute Map request){
        try{
//            ObjectMapper mapper = new ObjectMapper();
//            LocationDto locationDto = mapper.readValue(location, LocationDto.class);
//            KostPaymentScheme kostPaymentScheme = new KostPaymentScheme();
//            AddKostDto addKostDto= new AddKostDto();
//            addKostDto.setLocation(locationDto);
//            addKostDto.setName(request.getName());
//            addKostDto.setDescription(request.getDescription());
//            addKostDto.setImage_1(request.getImage_1());
//            addKostDto.setImage_2(request.getImage_2());
//            addKostDto.setType(request.getType());
            return new ResponseEntity<>(request, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            Map<String,Object> response = new LinkedHashMap<>();
            response.put("message","gagal");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }
}
