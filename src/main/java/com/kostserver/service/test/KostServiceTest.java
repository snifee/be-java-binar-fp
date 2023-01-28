package com.kostserver.service.test;

import com.kostserver.dto.request.AddKostDto;
import com.kostserver.dto.request.LocationDto;
import com.kostserver.repository.KostRepository;
import com.kostserver.service.KostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KostServiceTest {

    @Autowired
    private KostRepository kostRepository;

    @Autowired
    private KostService kostService;

    @Test
    public void test(){
        AddKostDto request = new AddKostDto();
        LocationDto locationDto = new LocationDto();
        locationDto.setLatitude("12");
        locationDto.setLongitude("12");
        locationDto.setCity("bali");
        locationDto.setAddress("jl. tirta empul");
        locationDto.setProvince("bali");
        locationDto.setDistrict("denpasar");

        request.setName("UHUY");

        try {
            kostService.addKost(request);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }




    }

}
