package com.kostserver.controller;


import com.kostserver.dto.LoginRequestDto;
import com.kostserver.dto.RegisterRequestDto;
import com.kostserver.service.LoginService;
import com.kostserver.service.OtpService;
import com.kostserver.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth/")
public class AuthController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/register")
    ResponseEntity<Map> register(@RequestBody RegisterRequestDto requestDto){
        Map response = registerService.register(requestDto);
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<Map> login(@RequestBody LoginRequestDto requestDto){
        Map response = loginService.login(requestDto);
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }


    @PostMapping("/confirm")
    ResponseEntity<Map> confirmEmail(@RequestParam String otp){
        Map<String, Object > response = new HashMap<>();

        try{
            otpService.confirmOtp(otp);
            response.put("status","success");
            response.put("message","Account activated");
        }catch (Exception e){
            response.put("status","failed");
            response.put("message",e.getMessage());
        }

        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
