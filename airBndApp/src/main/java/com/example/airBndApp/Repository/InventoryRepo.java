package com.example.airBndApp.Repository;

import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepo extends JpaRepository<InventoryEntity,Long> {

    void deleteByDateAfterAndRoom(LocalDate Date, RoomEntity Room);
}
