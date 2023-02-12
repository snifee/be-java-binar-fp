package com.kostserver.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostserver.dto.request.ChangePasswordDto;
import com.kostserver.dto.request.ForgotPasswordRequestDto;
import com.kostserver.dto.request.LoginRequestDto;
import com.kostserver.dto.request.RegisterRequestDto;
import com.kostserver.model.EnumRole;
import com.kostserver.model.entity.Account;
import com.kostserver.model.response.Response;
import com.kostserver.model.response.UserDetailsRespond;
import com.kostserver.repository.AccountRepository;
import com.kostserver.service.ChangePasswordService;
import com.kostserver.service.auth.AccountService;
import com.kostserver.service.auth.ForgotPasswordService;
import com.kostserver.service.auth.LoginService;
import com.kostserver.service.OtpService;
import com.kostserver.service.auth.RegisterService;
import com.kostserver.utils.auth.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AccountRepository accountRepository;


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

    @PutMapping("/password")
    ResponseEntity<Response> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto request) {
        try {
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage(forgotPasswordService.forgotPassword(request));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(null);
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/google/{email}")
    ResponseEntity<Map> oauth2(@PathVariable("email") String email) {
        try {

            Account account = accountRepository.findByEmail(email).get();

            UserDetails userDetails = accountService.loadUserByUsername(account.getEmail());

            UserDetailsRespond udr = new UserDetailsRespond(account,account.getUserProfile());
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String,Object> userDetailResponse = objectMapper.convertValue(udr,Map.class);

            String jwt = jwtUtils.generateToken(userDetails);
            Map<String,Object> data = new LinkedHashMap<>();
            data.put("access_token",jwt);
            data.put("user_details",userDetailResponse);

            Map<String,Object> res = new LinkedHashMap<>();
            res.put("status",HttpStatus.OK.name());
            res.put("message","success");
            res.put("data",data);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            Map<String,Object> res = new LinkedHashMap<>();
            res.put("status",HttpStatus.BAD_REQUEST.name());
            res.put("message","failed");
            res.put("data",null);
            res.put("error",e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
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
