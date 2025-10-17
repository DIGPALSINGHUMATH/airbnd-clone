package com.example.airBndApp.Service;

import com.example.airBndApp.Entity.RoomEntity;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    void initializeRoomAYear(RoomEntity room);

    void deleteFutureInventory(RoomEntity room);

}
