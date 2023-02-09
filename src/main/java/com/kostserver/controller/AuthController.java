package com.kostserver.controller;


import com.kostserver.dto.request.ChangePasswordDto;
import com.kostserver.dto.request.ForgotPasswordRequestDto;
import com.kostserver.dto.request.LoginRequestDto;
import com.kostserver.dto.request.RegisterRequestDto;
import com.kostserver.model.EnumRole;
import com.kostserver.service.ChangePasswordService;
import com.kostserver.service.auth.ForgotPasswordService;
import com.kostserver.service.auth.LoginService;
import com.kostserver.service.OtpService;
import com.kostserver.service.auth.RegisterService;
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
    private ForgotPasswordService forgotPasswordService;


    @Autowired
    private LoginService loginService;

    @Autowired
    private OtpService otpService;



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

    @PostMapping("/password/{token}")
    ResponseEntity<Map> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto request,
                                       @PathVariable("token") String token) {
        try {
            return new ResponseEntity<>(forgotPasswordService.forgotPassword(request, token), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //CORS TEST
//    @GetMapping("/test")
//    ResponseEntity<List> test(){
//        List<String> list = new ArrayList<>();
//        list.add("hay");
//        list.add("hello");
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
}
