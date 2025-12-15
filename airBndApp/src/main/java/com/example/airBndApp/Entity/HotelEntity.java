package com.example.airBndApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Hotel")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    private String city;

    @Column(columnDefinition = "TEXT[]")
    private String[] photo;

    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;

    @Embedded
    private ContactInfo contactInfo;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity owner;

    @OneToMany(mappedBy = "hotel")
    @JsonIgnore
    private List<RoomEntity> rooms;



}
