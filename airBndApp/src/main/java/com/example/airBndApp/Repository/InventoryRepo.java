package com.example.airBndApp.Repository;

import com.example.airBndApp.Entity.HotelEntity;
import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Entity.RoomEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepo extends JpaRepository<InventoryEntity,Long> {

    void deleteByRoom(RoomEntity Room);

    @Query("""
            SELECT DISTINCT i.hotel
            FROM InventoryEntity i
            WHERE i.city = :city
                AND i.date BETWEEN :startDate AND :endDate
                AND i.closed = FALSE
                AND (i.totalCount - i.bookingCount - i.reservedCount) >= :roomCount
            GROUP BY i.hotel , i.room
            HAVING COUNT(i.date) = :dateCount
            """)
    Page<HotelEntity> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCount") Integer roomCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
            );

    @Query("""
            SELECT i
            FROM InventoryEntity i
            WHERE i.room.id = :roomId
                AND i.date BETWEEN :startDate AND :endDate
                AND i.closed = false
                AND (i.totalCount - i.bookingCount - i.reservedCount) >= :roomCount
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<InventoryEntity> findAndLockAvailableInventory(
            @Param("roomId") Long roomId,
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCount") Integer roomCount
    );



}
