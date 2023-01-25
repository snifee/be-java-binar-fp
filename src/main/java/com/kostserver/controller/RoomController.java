package com.kostserver.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kostserver.model.EnumKostType;
import com.kostserver.repository.RoomKostRepository;
import com.kostserver.service.RoomService;

@RestController
@RequestMapping("/v1/")
public class RoomController {
    @Autowired
    RoomService roomService;

    @Autowired
    RoomKostRepository roomKostRepository;

    @GetMapping("/search")
    ResponseEntity<Map> searchRoom(@RequestParam(value = "keyword", required = true) String keyword,
            @RequestParam(value = "label", required = false, defaultValue = "") String label,
            @RequestParam(value = "type", required = false, defaultValue = "") String type,
            @RequestParam(value = "price_min", required = false, defaultValue = "0") Double price_min,
            @RequestParam(value = "price_max", required = false, defaultValue = "9999999999") Double price_max,
            @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        try {
            // EnumKostType kostType = EnumKostType.valueOf(type);
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", HttpStatus.OK);
            response.put("data", roomService.searchRoom(keyword, label, type, price_min, price_max, size));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
