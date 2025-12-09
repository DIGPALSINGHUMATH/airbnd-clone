package com.example.airBndApp.Service.Impl;

import com.example.airBndApp.Dto.BookingDto;
import com.example.airBndApp.Dto.BookingRequestDto;
import com.example.airBndApp.Dto.GuestDto;
import com.example.airBndApp.Entity.*;
import com.example.airBndApp.Entity.Enum.BookingStatus;
import com.example.airBndApp.Repository.*;
import com.example.airBndApp.Service.BookingService;
import com.example.airBndApp.exception.ResourcesNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepo hotelRepo;
    private final RoomRepo roomRepo;
    private final InventoryRepo inventoryRepo;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;



    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequestDto bookingRequestDto) {

        log.info("Initialising booking for hotel : {} , room : {} , data {} - {} .",
                bookingRequestDto.getHotelId(),bookingRequestDto.getRoomId(),bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate());
        HotelEntity hotel = hotelRepo.findById(bookingRequestDto.getHotelId())
                .orElseThrow(() -> new ResourcesNotFoundException("this hotel id "+bookingRequestDto.getHotelId()+" not found"));
        RoomEntity room = roomRepo.findById(bookingRequestDto.getRoomId())
                .orElseThrow(()-> new ResourcesNotFoundException("on this hotel id : "+bookingRequestDto.getHotelId()+" on there not found this room id : "+bookingRequestDto.getRoomId()));

        List<InventoryEntity> inventoryList =   inventoryRepo.findAndLockAvailableInventory(room.getId(), bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate(),bookingRequestDto.getRoomCount());

        long daysCounts = ChronoUnit.DAYS.between(bookingRequestDto.getCheckInDate(), bookingRequestDto.getCheckOutDate())+1;
        log.info("daysCounts : {} and  inventoryList size : {}",daysCounts,inventoryList.size());

        if(inventoryList.size() != daysCounts){
            throw new IllegalStateException("Room is not available yet");
        }

//        reserve the room/ update the booked count of inventories
        log.info("save inventoryList {}", inventoryList);
        for(InventoryEntity inventory : inventoryList){
            log.info("set booking count : {}",inventory.getBookingCount()+ bookingRequestDto.getRoomCount());

            inventory.setReservedCount(inventory.getReservedCount()+ bookingRequestDto.getRoomCount());
        }
            log.info("save inventoryList bookCount");
        inventoryRepo.saveAll(inventoryList);

//        TODO: calculate dynamic price in future


//        Create the Booking
        BookingEntity booking = BookingEntity.builder().status(BookingStatus.RESERVED)
                .hotel(hotel)
                .Room(room)
                .checkInDate(bookingRequestDto.getCheckInDate())
                .checkOutDate(bookingRequestDto.getCheckOutDate())
                .user(getCurrentUser())
                .roomCount(bookingRequestDto.getRoomCount())
                .amount(BigDecimal.TEN)
                .build();

        bookingRepository.save(booking);
        return modelMapper.map(booking , BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuestBooking(Long bookingId, List<GuestDto> guests) {
        log.info("Initialising guest in the hotel ");
        BookingEntity booking = bookingRepository.findById(bookingId    )
                .orElseThrow(() -> new ResourcesNotFoundException("this hotel id "+bookingId+" not found"));

        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has been expired ");
        }
        if(booking.getStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Booking hasn't reserved ");
        }

        for(GuestDto guestDto : guests){
            GuestEntity guest = modelMapper.map(guestDto , GuestEntity.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);

        }
        booking.setStatus(BookingStatus.GUEST_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);


    }

    public boolean hasBookingExpired(BookingEntity booking){
        return booking.getCreateAt().plusMinutes(10).isBefore(LocalDateTime.now());

    }

    public UserEntity getCurrentUser(){
        UserEntity user = new UserEntity();
        user.setId(1L); // TODO: REMMOVE DUMMY USER
        return user;
    }

}
