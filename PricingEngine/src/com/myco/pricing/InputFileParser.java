package com.myco.pricing;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.myco.pricing.domain.CompetitorPrice;
import com.myco.pricing.domain.PricingStrategyInput;
import com.myco.pricing.domain.Product;
import com.myco.pricing.error.InputFileParserException;

public class InputFileParser {

/**
 * 
 * @param fileName
 * @return populated InputFileParserResult
 * @throws InputFileParserException
 */
	public PricingStrategyInput parse(String fileName) throws InputFileParserException {
		
		PricingStrategyInput result = new PricingStrategyInput();
		BufferedReader reader = null;
		int sectionCount;
		List<Product> products = new ArrayList<Product>();
		List<CompetitorPrice> competitorPrices = new ArrayList<CompetitorPrice>();
		try {
			reader = Files.newBufferedReader(Paths.get(fileName));
			// First line should be number of products
			sectionCount = Integer.parseInt(reader.readLine());
			for (int c = 0; c < sectionCount; c++) {
				products.add(createProduct(reader.readLine()));
			}
			// Next line should be number of prices
			sectionCount = Integer.parseInt(reader.readLine());
			for (int c = 0; c < sectionCount; c++) {
				competitorPrices.add(createPrice(reader.readLine()));
			}
			// ignore if more prices than counter
		} catch (IOException|NumberFormatException e) {
			InputFileParserException ifpe = new InputFileParserException();
			ifpe.addSuppressed(e);
			throw ifpe;
		}
		result.setProducts(products);
		result.setPrices(competitorPrices);
		return result;
	}
	
	private Product createProduct(String inputFileLine) throws InputFileParserException {
		String[] values = inputFileLine.split(" ");
		if (values.length != 3) {
			throw new InputFileParserException();
		}
		return new Product(values[0], values[1], values[2]);
	}

	private CompetitorPrice createPrice(String inputFileLine) throws InputFileParserException {
		String[] values = inputFileLine.split(" ");
		if (values.length != 3) {
			throw new InputFileParserException();
		}
		return new CompetitorPrice(values[0], values[1], values[2]);
	}
}
