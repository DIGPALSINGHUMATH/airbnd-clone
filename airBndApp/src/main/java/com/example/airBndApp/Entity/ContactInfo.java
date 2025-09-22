package com.example.airBndApp.Entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactInfo {
    private String address;
    private String location;
    private Integer phoneNumber;
}
