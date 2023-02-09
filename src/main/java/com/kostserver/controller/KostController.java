package com.kostserver.controller;

import com.kostserver.dto.request.AddKostDto;
import com.kostserver.dto.request.UpdateKostDto;
import com.kostserver.model.entity.Kost;
import com.kostserver.model.response.Response;
import com.kostserver.service.KostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/kost/")
@Slf4j
public class KostController {
    @Autowired
    private KostService kostService;

    @GetMapping("/")
    ResponseEntity<Response> getListKostOwner(){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Map<String,Object>> data = kostService.listOwnerKost(email);
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setError(e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    ResponseEntity<Response> add(@Valid @RequestBody AddKostDto request){
        try{
            Kost data = kostService.addKost(request);
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setError(e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    ResponseEntity<Response> updateKost(@Valid @RequestBody UpdateKostDto request){
        try{
            Kost data = kostService.updateKost(request);
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setError(e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Response> getKostDetail(@PathVariable("id") Long id){
        try{
            Kost data = kostService.kostDetail(id);
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setError(e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Response> deleteKostById(@PathVariable("id")Long id){
        try {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            Response response = new Response();
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("success");
            response.setData(kostService.deleteKostById(email,id));

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "failed",null,e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

}
