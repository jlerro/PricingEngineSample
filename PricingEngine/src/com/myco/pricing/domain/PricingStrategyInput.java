package com.myco.pricing.domain;

import java.util.List;

/**
 * Container for parser results
 * @author John Lerro
 *
 */
public class PricingStrategyInput {

		private List<Product> products;
		private List<CompetitorPrice> prices;
		
		public List<Product> getProducts() {
			return products;
		}
		public void setProducts(List<Product> products) {
			this.products = products;
		}
		public List<CompetitorPrice> getPrices() {
			return prices;
		}
		public void setPrices(List<CompetitorPrice> prices) {
			this.prices = prices;
		}
}
