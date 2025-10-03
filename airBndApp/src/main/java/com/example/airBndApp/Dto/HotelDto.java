package com.example.airBndApp.Dto;

import com.example.airBndApp.Entity.ContactInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
public class HotelDto {
    private Long id;

    private String name;
    private String city;
    private String[] photo;
    private String[] amenities;
    private ContactInfo contactInfo;
    private Boolean active;
}
