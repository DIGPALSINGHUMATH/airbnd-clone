package com.example.airBndApp.Strategy.Pricingstrategy;

import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Strategy.PricingStrategy;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy {
    private final PricingStrategy wrapped;



    @Override
    public BigDecimal calculatePrice(InventoryEntity inventory) {
        BigDecimal price = wrapped.calculatePrice((inventory));
        return price.multiply(inventory.getSurgeFactor());
    }
}
