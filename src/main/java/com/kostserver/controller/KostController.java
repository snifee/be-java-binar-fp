package com.kostserver.controller;

import com.kostserver.dto.request.AddKostDto;
import com.kostserver.dto.request.UpdateKostDto;
import com.kostserver.service.KostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/kost/")
@Slf4j
public class KostController {
    @Autowired
    private KostService kostService;

    @PostMapping("/add")
    ResponseEntity<Map> addkost(@Valid @RequestBody AddKostDto request){
        try{
            return new ResponseEntity<>(kostService.addKost(request), HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            Map<String,Object> response = new LinkedHashMap<>();
            response.put("message","gagal");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    ResponseEntity<Map> updateKost(@Valid @RequestBody UpdateKostDto request){
        try{
            return new ResponseEntity<>(kostService.updateKost(request), HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            Map<String,Object> response = new LinkedHashMap<>();
            response.put("message","gagal");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }
}
