package com.example.airBndApp.Dto;

import com.example.airBndApp.Entity.Enum.Gender;
import com.example.airBndApp.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private UserDto user;
}
