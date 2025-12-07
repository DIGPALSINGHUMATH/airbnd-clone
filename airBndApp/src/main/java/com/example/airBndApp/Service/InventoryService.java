package com.example.airBndApp.Service;

import com.example.airBndApp.Entity.RoomEntity;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    void initializeRoomAYear(RoomEntity room);

    void deleteAllInventory(RoomEntity room);

}
