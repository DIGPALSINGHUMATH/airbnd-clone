package com.example.airBndApp.Service;

import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Entity.RoomEntity;
import com.example.airBndApp.Repository.InventoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceinpl implements InventoryService{

    private final InventoryRepo inventoryRepo;

    @Override
    public void initializeRoomAYear(RoomEntity room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        for(; !today.isAfter(endDate);today=today.plusDays(1)){
            InventoryEntity inventoryEntity = InventoryEntity.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookingCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepo.save(inventoryEntity);
        }


    }

    @Override
    public void deleteFutureInventory(RoomEntity room) {
        LocalDate today = LocalDate.now();
        inventoryRepo.deleteByDateAfterAndRoom(today,room);
    }
}
