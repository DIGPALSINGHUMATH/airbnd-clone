package com.example.airBndApp.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "Room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String Type;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal basePrice;

    @Column(nullable = false , columnDefinition = "TEXT[]")
    private String[] amenities;

    @Column(nullable = false, columnDefinition = "TEXT[]")
    private String[] photo;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(nullable = false )
    private Integer totalCount;

    @Column(nullable = false)
    private  Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;


}
