package com.example.airBndApp.Service;

import com.example.airBndApp.Dto.RoomDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    RoomDto createNewRoom(Long hotelId ,RoomDto roomDto);

    RoomDto getRoomById( Long id);

    List<RoomDto> getAllRoomByHotelId(Long hotelId);

    void deleteRoomById( Long id);

}
