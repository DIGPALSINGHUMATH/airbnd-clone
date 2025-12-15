package com.example.airBndApp.Strategy.Pricingstrategy;

import com.example.airBndApp.Entity.InventoryEntity;
import com.example.airBndApp.Strategy.PricingStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPricing(InventoryEntity inventory){

        PricingStrategy pricingStrategy = new BasePricingStrategy();

//        apply the additional strategies
        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);
    }
}
