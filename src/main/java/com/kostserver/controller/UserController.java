package com.kostserver.controller;


import com.kostserver.dto.UpdateBankAccountDto;
import com.kostserver.dto.UserVerificationDto;
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

    @PostMapping("/bank")
    ResponseEntity<Map> updateUserBank(@Valid @RequestBody UpdateBankAccountDto request){
        try {

            return new ResponseEntity<>(userService.updateUserBank(request),HttpStatus.OK);

        }catch (Exception e){
            Map response = new LinkedHashMap();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message",e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/verification")
    ResponseEntity<Map> updateUserVerification(@Valid @ModelAttribute UserVerificationDto request){
        try{
            return new ResponseEntity<>(userService.updateUserVerification(request),HttpStatus.OK);
        }catch (Exception e){
            Map response = new LinkedHashMap();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message",e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }
}