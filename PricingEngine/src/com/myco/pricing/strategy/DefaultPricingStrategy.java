package com.myco.pricing.strategy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.myco.pricing.domain.CompetitorPrice;
import com.myco.pricing.domain.PricingStrategyInput;
import com.myco.pricing.domain.Product;

public class DefaultPricingStrategy implements PricingStrategy {

	public static final String SD_HIGH = "H";
	public static final String SD_LOW = "L";

	/**
	 * For the design exercise, pricing will be output to the console only
	 */
	@Override
	public Map<Product, BigDecimal> determinePricing(PricingStrategyInput pricingInput) {

		// Loop by product code and store price / occurrences
		Map<Product, BigDecimal> returnMap = new HashMap<Product, BigDecimal>();
		BigDecimal thisPrice = null;
		for (Product thisProduct : pricingInput.getProducts()) {
			double averagePrice = 0d;
			int averageCounter = 0;
			// Get all competitor prices for this product
			Map<BigDecimal, Integer> productPrices = new TreeMap<BigDecimal, Integer>();
			for (CompetitorPrice price : pricingInput.getPrices()) {
				if (price.getProductCode()
						.equals(thisProduct.getProductCode())) {
					thisPrice = price.getPrice();
					averagePrice += thisPrice.doubleValue();
					averageCounter++;
					// Is price in map yet?
					if (!productPrices.containsKey(thisPrice)) {
						productPrices.put(thisPrice, new Integer(1));
					} else {
						// increment counter
						productPrices.put(thisPrice,
								new Integer(productPrices.get(thisPrice).intValue() + 1));
					}
				}
			}
			averagePrice = averagePrice / averageCounter;
			//System.out.println("Product " + thisProduct.getProductCode());
			//System.out.println("Found " + averageCounter + " averaging " + averagePrice);
			
			// Remove promotional prices (<50%) and assumed data errors (>50%). Could have been done with Streams
			Map<BigDecimal, Integer> fixedProductPrices = new TreeMap<BigDecimal, Integer>();
			Set<BigDecimal> keys = productPrices.keySet();
			for (BigDecimal key : keys) {
				if (key.doubleValue() < (averagePrice * .5) ||
					key.doubleValue() > averagePrice * 1.5) {
					//System.out.println("Skipping key " + key.toString());
				} else {
					fixedProductPrices.put(key, productPrices.get(key));
				}
			}
			productPrices = fixedProductPrices;
			keys = productPrices.keySet();

			//System.out.println("Product " + thisProduct.getProductCode());
			//for (BigDecimal key : keys) {
			//	System.out.println("Price " + key + " occurrences " + productPrices.get(key));
			//}

			// Determine price to use
			BigDecimal priceToUse = null;
			int occurrences = 0;
			for (BigDecimal key : keys) {
				if (productPrices.get(key).intValue() > occurrences) {
					// Use the most frequently occurring value
					priceToUse = key;
					occurrences = productPrices.get(key).intValue();
				} else if (productPrices.get(key).intValue() == occurrences) {
					// Use the lesser value
					if (key.doubleValue() < priceToUse.doubleValue()) {
						priceToUse = key;
						occurrences = productPrices.get(key).intValue();
					}
				}
			}
			//System.out.println("Unadjusted price to use " + priceToUse);
			double multiplier = 0d;
			if (thisProduct.getSupply().equals(SD_HIGH) && thisProduct.getDemand().equals(SD_HIGH)) {
				multiplier = 1;
			} else if (thisProduct.getSupply().equals(SD_LOW) && thisProduct.getDemand().equals(SD_LOW)) {
				//Supply is Low and Demand is Low, Product is sold at 10 % more than chosen price.
				multiplier = 1.1;
			} else if (thisProduct.getSupply().equals(SD_LOW) && thisProduct.getDemand().equals(SD_HIGH)) {
				//Supply is Low and Demand is High, Product is sold at 5 % more than chosen price.
				multiplier = 1.05;
			} else if (thisProduct.getSupply().equals(SD_HIGH) && thisProduct.getDemand().equals(SD_LOW)) {
				//Supply is High and Demand is Low, Product is sold at 5 % less than chosen price.			
				multiplier = .95;
			}
			// Rounding errors require use of scale here - problem statement says 1 decimal
			priceToUse = new BigDecimal(priceToUse.doubleValue() * multiplier).setScale(1, BigDecimal.ROUND_HALF_UP);
			
			//System.out.println(thisProduct.getProductCode() + " " + priceToUse);
			returnMap.put(thisProduct, priceToUse);
		}
		return returnMap;
	}
}
