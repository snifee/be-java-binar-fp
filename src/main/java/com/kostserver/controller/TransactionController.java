package com.kostserver.controller;

import com.kostserver.dto.request.BookingDto;
import com.kostserver.model.response.Response;
import com.kostserver.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/booking")
    ResponseEntity<Response> booking(@Valid @RequestBody BookingDto request){
        try{
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(transactionService.booking(request));
            response.setMessage("booking success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("booking failed");
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
