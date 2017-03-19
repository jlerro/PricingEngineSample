package com.myco.pricing.domain;

public class Product {

	private String productCode;
	private String supply;
	private String demand;
	
	public Product(String productCode, String supply, String demand)  {
		this.productCode = productCode;
		this.supply = supply;
		this.demand = demand;
		// debugging
		//System.out.println("Product: " + toString());
	}

	public String getProductCode() {
		return productCode;
	}

	public String getSupply() {
		return supply;
	}

	public String getDemand() {
		return demand;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(productCode).append(",");
		buffer.append(supply).append(",");
		buffer.append(demand);
		return buffer.toString(); 
	}
}
