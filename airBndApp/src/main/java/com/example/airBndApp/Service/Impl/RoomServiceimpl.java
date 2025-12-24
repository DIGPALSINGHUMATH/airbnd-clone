package com.example.airBndApp.Service.Impl;

import com.example.airBndApp.Dto.RoomDto;
import com.example.airBndApp.Entity.HotelEntity;
import com.example.airBndApp.Entity.RoomEntity;
import com.example.airBndApp.Entity.UserEntity;
import com.example.airBndApp.Repository.HotelRepo;
import com.example.airBndApp.Repository.RoomRepo;
import com.example.airBndApp.Service.InventoryService;
import com.example.airBndApp.Service.RoomService;
import com.example.airBndApp.exception.ResourcesNotFoundException;
import com.example.airBndApp.exception.UnAuthorisedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceimpl implements RoomService {

    private final HotelRepo hotelRepo;
    private final RoomRepo roomRepo;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;


    private Boolean isRoomIdExit(Long id){ // check it room is exit in inventory or not
        Boolean isRoomIdExit = roomRepo.existsById(id);
        if(!isRoomIdExit) throw  new ResourcesNotFoundException("Room are not exist this id : " + id);
        return true;
    }


    @Override
    public RoomDto createNewRoom(Long hotelId,RoomDto roomDto) {//create a room with details
        log.info("start find hotel");
        HotelEntity hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new ResourcesNotFoundException("Hotel id not Found : "+ hotelId));
        UserEntity user =(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw  new UnAuthorisedException("This user does not own this room. user id : "+ user.getId());
        }

        log.info("Room DTO: " + roomDto);
        RoomEntity roomEntity = modelMapper.map(roomDto,RoomEntity.class);
        roomEntity.setHotel(hotel);
        log.info("hotel set");
        roomEntity = roomRepo.save(roomEntity);
        log.info("room created");

//        if hotel active then create room in inventory to avaliblity to user
        if(hotel.getActive()){
            inventoryService.initializeRoomAYear(roomEntity);
        }

        return modelMapper.map(roomEntity , RoomDto.class);

    }

    @Override
    public RoomDto getRoomById( Long id) { //room find by id

        RoomEntity room = roomRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("room id not Found : "+ id));
        return modelMapper.map(room,RoomDto.class);

    }

    @Override
    public List<RoomDto> getAllRoomByHotelId(Long hotelId) { // get all room on hotal using id
        HotelEntity hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new ResourcesNotFoundException("Hotel id not Found : "+ hotelId));

        return hotel.getRooms().stream().map(element ->modelMapper.map(element , RoomDto.class)).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteRoomById(Long id) { // delete hotel using id
        RoomEntity room = roomRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("room id not Found : "+ id));

        UserEntity user =(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(room.getHotel().getOwner())){
            throw  new UnAuthorisedException("This user does not own this room. user id : "+ user.getId());
        }

        log.info("inventory delete : start");
        inventoryService.deleteAllInventory(room);
        log.info("room deleted now ");
        roomRepo.deleteById(id);
    }
}
