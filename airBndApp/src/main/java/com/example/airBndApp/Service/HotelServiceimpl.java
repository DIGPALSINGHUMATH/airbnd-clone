package com.example.airBndApp.Service;

import com.example.airBndApp.Dto.HotelDto;
import com.example.airBndApp.Entity.HotelEntity;
import com.example.airBndApp.Repository.HotelRepo;
import com.example.airBndApp.exception.ResourcesNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceimpl implements HotelService{

    private final HotelRepo hotelRepo;
    private final ModelMapper modelMapper;


    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.trace("create a Hotel");
        HotelEntity hotel =modelMapper.map(hotelDto , HotelEntity.class);
        hotel.setActive(false);
        hotelRepo.save(hotel);
        log.info("saved hotel data");
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting hotel by id {}",id);
        HotelEntity hotel = hotelRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Hotel id not Found : "+ id));
        hotel.setId(id);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("find hotel by id : {}",id);
        HotelEntity hotel = hotelRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("this hotel id "+id+" are not found"));
        hotel = modelMapper.map(hotelDto,HotelEntity.class);
        log.info("update value of this hotel id : {} info : {} ",id,hotel);
        hotel.setId(id);
        return modelMapper.map(hotelRepo.save(hotel), HotelDto.class);

    }

    @Override
    public void deleteHotelById(Long id) {
        Boolean isExist = hotelRepo.existsById(id);

    if(!isExist)  throw new ResourcesNotFoundException("for delete Hotel id is not found : {}"+id);

    log.info("start delete hotel on this  id {} ", id);

    hotelRepo.deleteById(id);

    //TODO : DELETE FUTURE INVENTERY DATA TO FOR THIS INVENTERY.

    }

    @Override
    public void activeHotel(Long id) {
        HotelEntity hotel = hotelRepo.findById(id).orElseThrow(() -> new ResourcesNotFoundException("this hotel id "+id+" are not found"));
        hotel.setActive(true);

//        TODO : CREATE INVENTORY FOR ALL THE ROOM FOR THIS HOTEL

    }
}
