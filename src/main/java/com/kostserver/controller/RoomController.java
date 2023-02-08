package com.kostserver.controller;

import com.kostserver.dto.ItemRoomDto;
import com.kostserver.dto.request.AddRatingRequest;
import com.kostserver.dto.request.RoomDto;
import com.kostserver.model.entity.RoomKost;
import com.kostserver.model.response.Response;
import com.kostserver.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/rooms/")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping("/add")
    ResponseEntity<Response> addRoom(@RequestBody RoomDto request){
        try {
            RoomKost room = roomService.addRoom(request);
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),"success",room,null), HttpStatus.OK);
        }catch (Exception e){

            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),null,null,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    ResponseEntity<Response> updateRoom(@RequestBody RoomDto request, @PathVariable("id") Long id){
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            RoomKost room = roomService.updateRoom(email,id,request);
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),"success",room,null), HttpStatus.OK);
        }catch (Exception e){

            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),null,null,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    ResponseEntity<Response> listOwnerRoom(){
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            List<ItemRoomDto> data = roomService.listOwnerRoom(email);
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),"success",data,null), HttpStatus.OK);
        }catch (Exception e){

            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),null,null,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{kostId}")
    ResponseEntity<Response> listRoomByKostId(@PathVariable("kostId")Long id){
        try {
            List<ItemRoomDto> data = roomService.listRoomByKostId(id);
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),"success",data,null), HttpStatus.OK);
        }catch (Exception e){

            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),null,null,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/rating")
    ResponseEntity<Response> addRating(@Valid @RequestBody AddRatingRequest request) {
        try {

            Response response = new Response();
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("success");
            response.setData(roomService.addRating(request));

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "failed",null,e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

}
