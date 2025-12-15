package com.example.airBndApp.Strategy.Pricingstrategy;

import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Strategy.PricingStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BasePricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(InventoryEntity inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
