package com.example.airBndApp.Service.Impl;

import com.example.airBndApp.Entity.HotelEntity;
import com.example.airBndApp.Entity.HotelMinPriceEntity;
import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Repository.HotelMinPriceRepository;
import com.example.airBndApp.Repository.HotelRepo;
import com.example.airBndApp.Repository.InventoryRepo;
import com.example.airBndApp.Strategy.Pricingstrategy.PricingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PricingUpdateService {

// scheduler to  update the inventory and hotelMinPrice table every hour

    private final InventoryRepo inventoryRepo;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final HotelRepo hotelRepo;
    private final PricingService pricingService;

//    @Scheduled(cron = "*/5 * * * * *")
    @Scheduled(cron = "0 */5 * * * *")
    public void updatePricing(){
        int page = 0 ;
        int batchsize = 100;
        while (true){
            Page<HotelEntity> hotelPage = hotelRepo.findAll(PageRequest.of(page,batchsize));
            if(hotelPage.isEmpty()){
                break;
            }

            hotelPage.getContent().forEach(this::updateHotelPrice);

            page++;


        }
    }

    private void updateHotelPrice(HotelEntity hotel){
        log.info("Updating pricing for hotel id : {}", hotel.getId());
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusYears(1);

        List<InventoryEntity> inventoryList = inventoryRepo.findByHotelAndDateBetween(hotel, startDate, endDate);

        updateInventoryPrice(inventoryList);

        updateHotelMinPrice(hotel, inventoryList, startDate , endDate);

    }

        private void updateHotelMinPrice(HotelEntity hotel, List<InventoryEntity> inventoryList, LocalDate startDate, LocalDate endDate) {
            Map<LocalDate, BigDecimal> dailyMinPrices = inventoryList.stream()
                    .collect(Collectors.groupingBy(
                            InventoryEntity::getDate,
                            Collectors.mapping(InventoryEntity::getPrice, Collectors.minBy(Comparator.naturalOrder()))
                    ))
                    .entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().orElse(BigDecimal.ZERO)));

            // Prepare HotelPrice entities in bulk
            List<HotelMinPriceEntity> hotelPrices = new ArrayList<>();
            dailyMinPrices.forEach((date, price) -> {
                HotelMinPriceEntity hotelPrice = hotelMinPriceRepository.findByHotelAndDate(hotel, date)
                        .orElse(new HotelMinPriceEntity(hotel, date));
                hotelPrice.setPrice(price);
                hotelPrices.add(hotelPrice);
            });
            hotelMinPriceRepository.saveAll(hotelPrices);
        }

    private void updateInventoryPrice(List<InventoryEntity> inventoryList){
        inventoryList.forEach(inventory -> {
            BigDecimal DynamicPrice = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(DynamicPrice);
        });
        inventoryRepo.saveAll(inventoryList);
    }

}
