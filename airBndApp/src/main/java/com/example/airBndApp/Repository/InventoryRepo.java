package com.example.airBndApp.Repository;

import com.example.airBndApp.Entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepo extends JpaRepository<InventoryEntity,Long> {
}
