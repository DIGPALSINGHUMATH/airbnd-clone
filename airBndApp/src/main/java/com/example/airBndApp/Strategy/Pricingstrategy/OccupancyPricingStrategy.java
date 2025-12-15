package com.example.airBndApp.Strategy.Pricingstrategy;

import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Strategy.PricingStrategy;
import lombok.RequiredArgsConstructor;


import java.math.BigDecimal;


@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(InventoryEntity inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        Double OccupancyCount = (double) (inventory.getBookingCount() / inventory.getTotalCount());
        if (OccupancyCount >= 0.8) {
            return price.multiply(BigDecimal.valueOf(1.2));
        }
        return price;
    }
}
