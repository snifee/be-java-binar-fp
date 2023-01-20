package com.kostserver.controller;


import com.kostserver.dto.ChangePasswordDto;
import com.kostserver.dto.LoginRequestDto;
import com.kostserver.dto.RegisterRequestDto;
import com.kostserver.model.EnumRole;
import com.kostserver.service.ChangePasswordService;
import com.kostserver.service.LoginService;
import com.kostserver.service.OtpService;
import com.kostserver.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/v1/auth/")
public class AuthController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ChangePasswordService changePasswordService;

    @Autowired
    private OtpService otpService;

    private List<String> coffee = new ArrayList<>();



    @PostMapping("/pemilik/register")
    ResponseEntity<Map> registerPenyedia(@Valid @RequestBody RegisterRequestDto request){
        Map response = registerService.register(request, EnumRole.ROLE_USER_PEMILIK);
        return new ResponseEntity<Map>(response, (HttpStatus) response.get("status"));
    }

    @PostMapping("/pencari/register")
    ResponseEntity<Map> registerPenyewa(@Valid @RequestBody RegisterRequestDto request){
        Map response = registerService.register(request, EnumRole.ROLE_USER_PENCARI);
        return new ResponseEntity<Map>(response, (HttpStatus) response.get("status"));
    }

    @PostMapping("/login")
    ResponseEntity<Map> login(@Valid @RequestBody LoginRequestDto request){
        Map response = loginService.login(request);
        return new ResponseEntity<Map>(response, (HttpStatus) response.get("status"));
    }

    @PutMapping("/password")
    ResponseEntity<Map> changePassword(@Valid @RequestBody ChangePasswordDto request){
        try{
            return new ResponseEntity<>(changePasswordService.changePassword(request),HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status",HttpStatus.BAD_REQUEST);
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }


    @PostMapping("/confirm")
    ResponseEntity<Map> confirmEmail(@RequestParam String otp){
        Map<String, Object > response = new HashMap<>();

        try{
            otpService.confirmOtp(otp);
            response.put("status",HttpStatus.OK);
            response.put("message","Account activated");
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_REQUEST);
            response.put("message",e.getMessage());
        }

        return new ResponseEntity<Map>(response, (HttpStatus) response.get("status"));
    }
}
