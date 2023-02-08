package com.kostserver.controller;

import com.kostserver.model.response.Response;
import com.kostserver.service.OwnerStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/statistic")
public class StatisticController {
    @Autowired
    private OwnerStatisticService ownerStatisticService;

    @GetMapping("/")
    ResponseEntity<Response> ownerStatistic(){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            Response response = new Response();

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("success");
            response.setData(ownerStatisticService.ownerStatistic(email));
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();

            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("failed");
            response.setError(e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }
}
