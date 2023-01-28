package com.kostserver.service.test;

import com.kostserver.dto.request.RoomDto;
import com.kostserver.model.entity.RoomKost;
import com.kostserver.service.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomServiceTest {

    @Autowired
    RoomService roomService;

    @Test
    public void test() {

        try{
            Map roomKost = roomService.getRoomDetailsById(2L);

            System.out.println(roomKost);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
