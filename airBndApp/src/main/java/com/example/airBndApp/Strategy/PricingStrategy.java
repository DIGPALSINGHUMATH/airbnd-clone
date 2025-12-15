package com.example.airBndApp.Strategy;

import com.example.airBndApp.Entity.InventoryEntity;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(InventoryEntity inventory);
}
