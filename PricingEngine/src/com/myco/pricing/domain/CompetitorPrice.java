package com.myco.pricing.domain;

import java.math.BigDecimal;

public class CompetitorPrice {

	private String productCode;
	private String competitorName;
	private BigDecimal price;
	
	public CompetitorPrice(String productCode, String competitorName, String price)  {
		this.productCode = productCode;
		this.competitorName = competitorName;
		this.price = new BigDecimal(price);
		// debugging
		//System.out.println("Price: " + toString());
	}

	public String getProductCode() {
		return productCode;
	}

	public String getCompetitorName() {
		return competitorName;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(productCode).append(",");
		buffer.append(competitorName).append(",");
		buffer.append(price.toString());
		return buffer.toString(); 
	}

}
