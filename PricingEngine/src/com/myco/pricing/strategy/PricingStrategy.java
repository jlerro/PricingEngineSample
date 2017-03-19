package com.myco.pricing.strategy;

import java.math.BigDecimal;
import java.util.Map;

import com.myco.pricing.domain.PricingStrategyInput;
import com.myco.pricing.domain.Product;

public interface PricingStrategy {

	public Map<Product, BigDecimal> determinePricing(PricingStrategyInput input);

}
