package com.kostserver.controller;

import com.kostserver.dto.request.AddKostDto;
import com.kostserver.dto.request.RoomDto;
import com.kostserver.dto.request.UpdateKostDto;
import com.kostserver.model.entity.RoomKost;
import com.kostserver.model.response.Response;
import com.kostserver.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/v1/room/")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping("/add")
    ResponseEntity<Response> addRoom(@RequestParam("file") MultipartFile[] file, @RequestBody RoomDto request){
        try {
            RoomKost room = this.roomService.addRoom(request, file);
            return new ResponseEntity<>(new Response(201,"success",room,null), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(500,"Fail to add!",null,null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    ResponseEntity<Response> updateRoom(@PathVariable("id") Long id, @RequestBody RoomDto request){
        try {
            RoomKost room = this.roomService.updateRoom(id, request);
            return new ResponseEntity<>(new Response(201,"success",room,null), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(500,"Fail to update!",null,null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id){
        if( this.roomService.delete(id)){
            return new ResponseEntity<>(new Response(200,"success",null,null), HttpStatus.OK);
        } else
        return new ResponseEntity<>(new Response(400,"Bad Request",null,null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("getRoom/{id}")
    public ResponseEntity<Response> getRoomById (@PathVariable("id") Long id) {
        RoomKost room = this.roomService.getRoomById(id);
        if(room!=null){
            return new ResponseEntity<>(new Response(200,"succsess",room,null),HttpStatus.OK);
        } else
            return new ResponseEntity<>(new Response(400,"Room ID Not Found",null,null),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("getOwner/{id}")
    public ResponseEntity<Response> getOwnerById (@PathVariable("id") Long id,
                                                  @RequestParam(value = "name") String name,
                                                  @RequestParam(value = "phone") String phone,
                                                  @RequestParam(value = "createdDate") Date createdDate) {
        RoomKost room = this.roomService.getOwnerById(id, name, phone, createdDate);
        if(room!=null){
            return new ResponseEntity<>(new Response(200,"succsess",room,null),HttpStatus.OK);
        } else
            return new ResponseEntity<>(new Response(400,"Room ID Not Found",null,null),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("getRoomDetails/{id}")
    public ResponseEntity<Response> getRoomDetailsById (@PathVariable("id") Long id) {
        RoomKost room = this.roomService.getRoomDetailsById(id);
        if(room!=null){
            return new ResponseEntity<>(new Response(200,"succsess",room,null),HttpStatus.OK);
        } else
            return new ResponseEntity<>(new Response(400,"Room ID Not Found",null,null),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchByKeyword(@RequestParam(value = "keyword") String keyword,
                                                    @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                    @RequestParam(value = "filter_minPrice",required = false,defaultValue = "0") Integer minPrice,
                                                    @RequestParam(value = "filter_maxPrice",required = false) Integer maxPrice,
                                                    @RequestParam(value = "filter_5Star",required = false,defaultValue = "false") Boolean is5StarRating
    ) {
        if(pageNo <= 0 ||pageSize <= 0 || keyword == null){
            return new ResponseEntity<>(new Response(400, "Invalid Params", null, null), HttpStatus.BAD_REQUEST);
        }

        if(minPrice==null){
            minPrice=0;
        }
        if(maxPrice==null){
            maxPrice=1000000000;
        }

        int rating ;
        if(is5StarRating==null || !is5StarRating){
            rating = (int) 0;
        }else {
            rating = 5;
        }

        List<RoomKost> roomList = roomService.findByKeyword(keyword, pageNo, pageSize, minPrice, maxPrice, rating);
        Map<String,Object> result= new HashMap<>();

        if(roomList!=null ||  (roomList ==null && pageNo>1)){
            result.put("found", true);
            result.put("roomKost", roomList);
        }
        return new ResponseEntity<>(new Response(200, "success", result, null), HttpStatus.OK);
    }
}
