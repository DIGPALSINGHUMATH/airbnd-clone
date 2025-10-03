package com.example.airBndApp.Dto;

import com.example.airBndApp.Entity.HotelEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDto {
    private Long id;

    private String Type;

    private BigDecimal basePrice;

    private String[] amenities;
    private String[] photo;


    private Integer totalCount;
    private  Integer capacity;

}
