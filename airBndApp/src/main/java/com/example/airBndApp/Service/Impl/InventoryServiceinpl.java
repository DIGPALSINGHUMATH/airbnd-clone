package com.example.airBndApp.Service.Impl;

import com.example.airBndApp.Dto.HotelDto;
import com.example.airBndApp.Dto.HotelSearchRequest;
import com.example.airBndApp.Entity.HotelEntity;
import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Entity.RoomEntity;
import com.example.airBndApp.Repository.InventoryRepo;
import com.example.airBndApp.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceinpl implements InventoryService {

    private final InventoryRepo inventoryRepo;
    private final ModelMapper modelMapper;

    @Override
    public void initializeRoomAYear(RoomEntity room) {  // create a room in inventory
        LocalDate today = LocalDate.now(); // current date
        LocalDate endDate = today.plusYears(1);  // one year
//        for is going today to whole one year
        for(; !today.isAfter(endDate);today=today.plusDays(1)){
            InventoryEntity inventoryEntity = InventoryEntity.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookingCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepo.save(inventoryEntity); // save inventory
        }


    }

    @Override
    public void deleteAllInventory(RoomEntity room) {
        log.info("delete room");
        inventoryRepo.deleteByRoom(room);
        log.info("complete deleted room");
    }

        @Override
        public Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
            Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());
            long dateCount = ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;

            Page<HotelEntity> hotelEntityPage = inventoryRepo.findHotelsWithAvailableInventory(hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(),hotelSearchRequest.getRoomCount(),
                    dateCount, pageable);


            return hotelEntityPage.map((hotelEntity -> modelMapper.map(hotelEntity, HotelDto.class)));
        }
}
