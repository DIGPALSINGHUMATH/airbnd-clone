package com.example.airBndApp.Repository;

import com.example.airBndApp.Entity.BookingEntity;
import com.fasterxml.jackson.databind.node.LongNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}
