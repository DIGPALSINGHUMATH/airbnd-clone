package com.example.airBndApp.Repository;

import com.example.airBndApp.Entity.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
}
