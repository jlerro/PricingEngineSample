package com.myco.pricing;

public class PricingEngineRunner {

	/**
	 * Usage: java -jar PricingEngineRunner [filename]
	 * 
	 * @param args Optional filename - defaults to input.txt
	 */
	public static void main(String[] args) {
		
		PricingEngine pricingEngine = new PricingEngine();
		pricingEngine.determinePrices(args.length == 0 ? PricingEngine.DEFAULT_FILENAME : args[0]);
	}
}
