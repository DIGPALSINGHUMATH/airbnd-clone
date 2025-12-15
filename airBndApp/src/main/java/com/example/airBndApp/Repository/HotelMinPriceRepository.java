package com.example.airBndApp.Repository;

import com.example.airBndApp.Dto.HotelPriceDto;
import com.example.airBndApp.Entity.HotelEntity;
import com.example.airBndApp.Entity.HotelMinPriceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.ScopedValue;
import java.time.LocalDate;
import java.util.Optional;

public interface HotelMinPriceRepository extends JpaRepository<HotelMinPriceEntity, Long> {

    @Query("""
            SELECT new com.example.airBndApp.Dto.HotelPriceDto(
                    i.hotel,
                    AVG(i.price)
                )
            FROM HotelMinPriceEntity i
            WHERE i.hotel.city = :city
                AND i.date BETWEEN :startDate AND :endDate
                AND i.hotel.active = TRUE
            GROUP BY i.hotel
            """)
    Page<HotelPriceDto> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCount") Integer roomCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );

    Optional<HotelMinPriceEntity> findByHotelAndDate(HotelEntity hotel, LocalDate date);

}