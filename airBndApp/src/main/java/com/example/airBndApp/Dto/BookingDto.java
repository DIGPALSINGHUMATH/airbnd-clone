package com.example.airBndApp.Dto;

import com.example.airBndApp.Entity.*;
import com.example.airBndApp.Entity.Enum.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private BookingStatus status;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer roomCount;
    private HotelDto hotel;
    private RoomDto Room;
    private UserDto user;
    private Set<GuestDto> guests;

}
