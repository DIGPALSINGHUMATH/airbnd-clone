package com.example.airBndApp.Service;

import com.example.airBndApp.Dto.BookingDto;
import com.example.airBndApp.Dto.BookingRequestDto;
import com.example.airBndApp.Dto.GuestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    BookingDto initialiseBooking(BookingRequestDto bookingRequestDto);

    BookingDto addGuestBooking(Long BookingId, List<GuestDto> guests);
}
