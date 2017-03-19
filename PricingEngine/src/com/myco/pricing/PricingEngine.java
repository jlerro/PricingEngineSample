package com.myco.pricing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.myco.pricing.domain.PricingStrategyInput;
import com.myco.pricing.domain.Product;
import com.myco.pricing.error.InputFileParserException;
import com.myco.pricing.strategy.DefaultPricingStrategy;
import com.myco.pricing.strategy.PricingStrategy;

/**
 * PricingEngine is a Service type object with one operation,
 * but could easily be extended.
 * 
 * @author John Lerro
 *
 */
public class PricingEngine {
	
	public static final String DEFAULT_FILENAME = "input1.txt";
	public static final int RETURN_CODE_SUCCESS = 0;
	public static final int RETURN_CODE_INVALID_FILE = 1;
	
	
	/**
	 * Method accepts an input filename of the specified format and
	 * outputs a recommended price for each input item based on rules.
	 * For demonstration purposes output will be to the console.
	 * 
	 * @param inputFilename
	 * @return int Result code - 0 = success, 1 = invalid file
	 *         (if this were a web service operation, a message might be returned too)
	 */
	public int determinePrices(String sourceFilename) {

		InputFileParser parser = new InputFileParser();
		PricingStrategyInput pricingInput = null;
		Map<Product, BigDecimal> results = new HashMap<Product, BigDecimal>();
		try {
			pricingInput = parser.parse(sourceFilename);
		} catch (InputFileParserException ifpe) {
			return RETURN_CODE_INVALID_FILE;
		}
		
		// Just one strategy for now
		PricingStrategy pricingStrategy = new DefaultPricingStrategy();
		results = pricingStrategy.determinePricing(pricingInput);
		displayResults(results);
		return RETURN_CODE_SUCCESS;
	}
	
	private void displayResults(Map<Product, BigDecimal> results) {
		
		int asciiLabel = 65; // Spec says to output A,B,C...
		Set<Product> keys = results.keySet();
		for (Product product : keys) {
//			System.out.println((char)asciiLabel + " " + results.get(product) + " (" + product.getProductCode() + ")");	
			System.out.println((char)asciiLabel + " " + results.get(product));
			asciiLabel++;
		}

	}
}
