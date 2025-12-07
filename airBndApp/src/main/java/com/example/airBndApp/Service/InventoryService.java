package com.example.airBndApp.Service;

import com.example.airBndApp.Dto.HotelDto;
import com.example.airBndApp.Dto.HotelSearchRequest;
import com.example.airBndApp.Entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    void initializeRoomAYear(RoomEntity room);

    void deleteAllInventory(RoomEntity room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
