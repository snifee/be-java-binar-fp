package com.kostserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostserver.model.mock.KostMockAPI;
import com.kostserver.model.mock.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.*;

@RestController
@RequestMapping("/mock-api")
public class MockController {

    @Operation(summary = "get info kost")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "request success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = KostMockAPI.class)))
    })
    @GetMapping("/kost")
    public ResponseEntity<Response> getKost() {
        String url = "https://63bab2a732d17a50907ad64a.mockapi.io/mock-api/kost";
        RestTemplate restTemplate = new RestTemplate();
        List result = restTemplate.getForObject(url,List.class);
        ArrayList<KostMockAPI> responseData= new ArrayList<>();

        String[] typeArray = {"Campuran","Khusus Pria","Khsus Perempuan"};
        String[] categoryArray = {"Murah","Fasilitas Lengkap","Eksklusif"};

        ArrayList<Object> types=new ArrayList<>();
        ArrayList<Object> categories=new ArrayList<>();

        for(int i=0;i<typeArray.length;i++){
            LinkedHashMap<String, Object> category =new LinkedHashMap<>();
            category.put("id",String.valueOf(i));
            category.put("name",categoryArray[i]);
            categories.add(category);
            LinkedHashMap<String, Object> type=new LinkedHashMap<>();
            type.put("id",String.valueOf(i));
            type.put("name",typeArray[i]);
            types.add(type);
        }

        for(int i=0;i<result.size();i++){
            ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            KostMockAPI item = mapper.convertValue(result.get(i),KostMockAPI.class);
            Random r = new Random();
            double rating =(Math.random() * ((5.0 - 0.0) + 0.1));
            DecimalFormat df = new DecimalFormat("#.##");

            String priceString  = String.valueOf((int)(Math.random() * ((1000 - 20) + 20)));
            int price = Integer.parseInt(priceString.concat("000"));

            item.setRating(Float.valueOf(df.format(rating)));
            item.setPrice(price);

            item.setType((Map<String, Object>) types.get(r.nextInt((types.size())-0) + 0));
            item.setCategories((Map<String, Object>) categories.get(r.nextInt((types.size()) - 0)));
            responseData.add(item);

        }
        return new ResponseEntity<>(new Response(200,"Succes",responseData,null), HttpStatus.OK);

    }

}
