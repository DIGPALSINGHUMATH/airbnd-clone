package com.example.airBndApp.Repository;

import com.example.airBndApp.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<RoomEntity,Long> {
}
