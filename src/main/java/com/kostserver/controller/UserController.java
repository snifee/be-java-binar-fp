package com.kostserver.controller;

import com.kostserver.dto.request.UpdateBankAccountDto;
import com.kostserver.dto.request.UpdateUserProfileDto;
import com.kostserver.dto.request.UserVerificationDto;
import com.kostserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/account/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    ResponseEntity<Map> getCurrentUser() {
        try {
            return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bank")
    ResponseEntity<Map> updateUserBank(@Valid @RequestBody UpdateBankAccountDto request) {
        try {

            return new ResponseEntity<>(userService.updateUserBank(request), HttpStatus.OK);

        } catch (Exception e) {
            Map response = new LinkedHashMap();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verification")
    ResponseEntity<Map> updateUserVerification(@Valid @ModelAttribute UserVerificationDto request) {
        try {
            return new ResponseEntity<>(userService.updateUserVerification(request), HttpStatus.OK);
        } catch (Exception e) {
            Map response = new LinkedHashMap();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profile")
    ResponseEntity<Map> updateProfile(@Valid @ModelAttribute UpdateUserProfileDto request) {
        try {
            return new ResponseEntity<>(userService.updateUserProfile(request), HttpStatus.OK);
        } catch (Exception e) {
            Map response = new LinkedHashMap();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
