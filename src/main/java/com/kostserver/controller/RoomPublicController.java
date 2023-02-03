package com.kostserver.controller;

import com.kostserver.dto.request.AddRatingRequest;
import com.kostserver.model.response.Response;
import com.kostserver.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/public")
public class RoomPublicController {



    @Autowired
    private RoomService roomService;

    @GetMapping("/search")
    ResponseEntity<Map> searchRoom(@RequestParam(value = "keyword", required = true) String keyword,
                                   @RequestParam(value = "label", required = false, defaultValue = "") String label,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "price_min", required = false, defaultValue = "0") Double price_min,
                                   @RequestParam(value = "price_max", required = false, defaultValue = "9999999999") Double price_max,
                                   @RequestParam(value = "size", required = false, defaultValue = "1") int size) {
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", HttpStatus.OK);
            response.put("data", roomService.searchRoom(keyword, label, type, price_min,
                    price_max, size));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/contact/{roomId}")
    ResponseEntity<Response> getRoomOwnerContact(@PathVariable("roomId") Long roomId){
        try{
            Map data = roomService.getOwnerContact(roomId);
            Response response = new Response(HttpStatus.OK.value(), "success",data,null);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "failed",null,e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rooms/{id}")
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


    @GetMapping("/rooms/rating")
    ResponseEntity<Response> addRating(@RequestParam(value = "id", required = true) Long id,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "1") int size) {

        try {

            Response response = new Response();
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("success");
            response.setData(roomService.getRating(id, page, size));

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "failed",null,e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }
}
