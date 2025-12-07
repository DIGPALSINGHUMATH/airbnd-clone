package com.example.airBndApp.Dto;

import com.example.airBndApp.Entity.Enum.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;

    private String name;

    private String email;

    private String Password;

    private Set<Role> role;
}
