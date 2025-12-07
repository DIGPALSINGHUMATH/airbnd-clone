package com.example.airBndApp.Controller;

import com.example.airBndApp.Dto.HotelDto;
import com.example.airBndApp.Dto.HotelInfoDto;
import com.example.airBndApp.Dto.HotelSearchRequest;
import com.example.airBndApp.Service.HotelService;
import com.example.airBndApp.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelBrowseController {

    public final InventoryService inventoryService;
    public  final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> hotelBrowse(@RequestBody HotelSearchRequest hotelSearchRequest){

        Page<HotelDto> hotelDtoPage = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(hotelDtoPage);

    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfo(hotelId));
    }

}
