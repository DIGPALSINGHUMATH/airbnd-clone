package com.example.airBndApp.Service.Impl;

import com.example.airBndApp.Dto.HotelDto;
import com.example.airBndApp.Entity.HotelEntity;
import com.example.airBndApp.Entity.RoomEntity;
import com.example.airBndApp.Repository.HotelRepo;
import com.example.airBndApp.Service.HotelService;
import com.example.airBndApp.Service.InventoryService;
import com.example.airBndApp.Service.RoomService;
import com.example.airBndApp.exception.ResourcesNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceimpl implements HotelService {

    private final HotelRepo hotelRepo;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomService roomService;



    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) { //creating hotel in database
        log.trace("create a Hotel");
        HotelEntity hotel =modelMapper.map(hotelDto , HotelEntity.class);
        hotel.setActive(false);
        hotelRepo.save(hotel);
        log.info("saved hotel data");
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) { // get data using hotelId
        log.info("Getting hotel by id {}",id);
        HotelEntity hotel = hotelRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Hotel id not Found : "+ id));
        hotel.setId(id);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) { //using hotel id to update hotel
        log.info("find hotel by id : {}",id);
        HotelEntity hotel = hotelRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("this hotel id "+id+" are not found"));
        hotel = modelMapper.map(hotelDto,HotelEntity.class);
        log.info("update value of this hotel id : {} info : {} ",id,hotel);
        hotel.setId(id);
        return modelMapper.map(hotelRepo.save(hotel), HotelDto.class);

    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {// using id delete hotel including inventory
        HotelEntity hotel = hotelRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("this hotel id "+id+" are not found"));

        log.info("start delete hotel on this  id {} ", id);

//    loop to delete hotel room from inventory
        for(RoomEntity room : hotel.getRooms()){
            inventoryService.deleteAllInventory(room); // deleting room from inventory name by hotel this id;
            roomService.deleteRoomById(room.getId()); // deleting from hotel room
        }
        log.info("delete hotel on this id : {}", id);
        hotelRepo.deleteById(id); //delete hotel
        log.info("deleted hotel on this id : {}", id);


    }

    @Override
    @Transactional
    public void activeHotel(Long id) { //patch active feild to live in inventory
        HotelEntity hotel = hotelRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("this hotel id "+id+" are not found"));
        hotel.setActive(true);
//          create a room in inventory to one year
        for(RoomEntity room: hotel.getRooms() ){
            inventoryService.initializeRoomAYear(room);
        }

    }
}
