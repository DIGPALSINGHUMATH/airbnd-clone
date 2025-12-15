package com.example.airBndApp.Strategy.Pricingstrategy;

import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Strategy.PricingStrategy;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(InventoryEntity inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        boolean isHoliday = true;// TODO : ADD EXTERNAL API TO CHECK HOLIDAY
        if (isHoliday){
            price = price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }
}
