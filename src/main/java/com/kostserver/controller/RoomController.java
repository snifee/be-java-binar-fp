package com.kostserver.controller;

import com.kostserver.dto.request.RoomDto;
import com.kostserver.model.entity.RoomKost;
import com.kostserver.model.response.Response;
import com.kostserver.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
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
    ResponseEntity<Response> addRoom(@RequestBody RoomDto request, @PathVariable("id") String id){
        try {
            Long roomId = Long.parseUnsignedLong(id);
            RoomKost room = roomService.updateRoom(roomId,request);
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(),"success",room,null), HttpStatus.OK);
        }catch (Exception e){

            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),null,null,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Response> getRoomDetail(@PathVariable("id") Long id){
        try{
            Map data = roomService.getRoomDetailsById(id);
            Response response = new Response(HttpStatus.OK.value(), "success",data,null);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "failed",null,e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

}
