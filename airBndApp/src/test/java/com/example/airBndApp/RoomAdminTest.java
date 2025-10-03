package com.example.airBndApp;

import com.example.airBndApp.Dto.RoomDto;
import com.example.airBndApp.Service.RoomServiceimpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoomAdminTest {

    @Autowired
    public RoomServiceimpl roomServiceimpl;

    @Test
    public void roomCreate(){
//        roomServiceimpl.createNewRoom(1,new RoomDto());
    }



}
