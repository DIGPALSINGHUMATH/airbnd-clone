package com.example.airBndApp.Entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
public class HotelMinPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ContactInfo contactInfo;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id" , nullable = false)
    private HotelEntity hotel;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; //cheapest price or the room in this hotel on that date

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;


    public HotelMinPriceEntity(HotelEntity hotel, LocalDate date) {
        this.hotel = hotel;
        this.date = date;
    }


}
