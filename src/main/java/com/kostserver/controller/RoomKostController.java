package com.kostserver.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.KostDetailDto;
import com.kostserver.dto.RoomKostDto;
import com.kostserver.model.request.CreateKostRequest;
import com.kostserver.model.response.Response;
import com.kostserver.service.KostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/kost")
public class RoomKostController {

    @Autowired
    KostService kostService;

    @PostMapping("/upload")
    public ResponseEntity<Object> getImageUrl (@RequestParam(value = "file") MultipartFile file) {
        String contentType = file.getContentType();
        if(!(contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg"))){
            return new ResponseEntity<>(new Response(400,"Pleas Upload Image File",null,null), HttpStatus.BAD_REQUEST);
        }
        if(file.getSize()>10000000){
            return new ResponseEntity<>(new Response(400,"Image too Large",null,null), HttpStatus.BAD_REQUEST);
        }
        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "kosthub",
                    "api_key", "618124265592915",
                    "api_secret", "tjgZHec24_bcZZFCeVSd0ysL2qs"));
            Map response = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            return new ResponseEntity<>(new Response(200,"Upload Success",response.get("url"),null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response(500,"Failed Upload File",null,null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<Response> getRoomKost (@PathVariable Long id) {

        KostDetailDto roomKost = this.kostService.getById(id);
        if(roomKost!=null){
            return new ResponseEntity<>(new Response(200,"succsess",roomKost,null),HttpStatus.OK);
        }else return new ResponseEntity<>(new Response(400,"Room Kost ID Not Found",null,null),HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id){
        if( this.kostService.delete(id)){
            return new ResponseEntity<>(new Response(200,"success",null,null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(400,"Bad Request",null,null), HttpStatus.BAD_REQUEST);

    }

    @PostMapping ("/insert/roomKost")
    public ResponseEntity<Response> createKostRequest(@RequestBody RoomKostDto roomKostDto){
        try {
            CreateKostRequest productDtoInsert=this.kostService.insert(roomKostDto);
            return new ResponseEntity<>(new Response(201,"success",productDtoInsert,null), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(500,"Fail to insert!",null,null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
