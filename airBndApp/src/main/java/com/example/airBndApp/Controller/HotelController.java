package com.example.airBndApp.Controller;

import com.example.airBndApp.Dto.HotelDto;
import com.example.airBndApp.Service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/hotel")
public class HotelController {
    private final HotelService hotelService;


    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
        return new ResponseEntity<>(hotelService.createNewHotel(hotelDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long id ,@RequestBody HotelDto hotelDto){
        return ResponseEntity.ok(hotelService.updateHotelById(id,hotelDto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long id){
        hotelService.deleteHotelById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> activeHotel(@PathVariable Long id){
        hotelService.activeHotel(id);
        return ResponseEntity.noContent().build();
    }



}
















