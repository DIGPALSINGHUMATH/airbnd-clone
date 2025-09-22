package com.example.airBndApp.Entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Inventory",
uniqueConstraints = @UniqueConstraint(
        name = "unique_hotel_room_date",
        columnNames = {"hotel_id","room_id","date"}
))
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false,columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookingCount;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false)
    private String city;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false)
    private Boolean closed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id" , nullable = false)
    private HotelEntity hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private RoomEntity room;


}
