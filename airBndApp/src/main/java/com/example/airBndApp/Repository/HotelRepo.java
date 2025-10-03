package com.example.airBndApp.Repository;

import com.example.airBndApp.Entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<HotelEntity,Long> {
}
