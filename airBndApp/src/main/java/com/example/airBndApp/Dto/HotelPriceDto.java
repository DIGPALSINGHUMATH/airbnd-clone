package com.example.airBndApp.Dto;

import com.example.airBndApp.Entity.HotelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceDto {
    private HotelEntity hotel;
    private Double price;

}
