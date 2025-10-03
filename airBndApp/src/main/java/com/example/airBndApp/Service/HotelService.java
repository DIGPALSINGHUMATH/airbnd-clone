package com.example.airBndApp.Service;

import com.example.airBndApp.Dto.HotelDto;
import org.springframework.stereotype.Service;

@Service
public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activeHotel(Long id);
}
