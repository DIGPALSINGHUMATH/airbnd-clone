package com.example.airBndApp.Controller;

import com.example.airBndApp.Dto.BookingDto;
import com.example.airBndApp.Dto.BookingRequestDto;
import com.example.airBndApp.Dto.GuestDto;
import com.example.airBndApp.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("init")
    public ResponseEntity<BookingDto>  initialiseBooking(@RequestBody BookingRequestDto bookingRequestDto){
    return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequestDto));
    }

    @PostMapping("/{bookingId}/guest")
    public ResponseEntity<BookingDto> addGuestBooking(@PathVariable Long bookingId , @RequestBody List<GuestDto> guests){
        return ResponseEntity.ok(bookingService.addGuestBooking(bookingId, guests));
    }
}
