package com.myco.pricing.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.myco.pricing.InputFileParser;
import com.myco.pricing.PricingEngine;
import com.myco.pricing.domain.PricingStrategyInput;
import com.myco.pricing.error.InputFileParserException;

public class InputFileParserTest {

	@Test
	public void testValidFile() {
		InputFileParser parser = new InputFileParser();
		PricingStrategyInput result = null;
		// test success with default file
		try {
			result = parser.parse(PricingEngine.DEFAULT_FILENAME);
		} catch (InputFileParserException ifpe) {
			fail();
		}
		assertNotNull(result);
	}
	
	@Test
	public void testInvalidFile() {
		InputFileParser parser = new InputFileParser();
		PricingStrategyInput result = null;
		try {
			result = parser.parse("input_bad.txt");
			fail();
		} catch (InputFileParserException ifpe) {
			assertNull(result);
		}
	}

	@Test
	public void testNonexistentFile() {
		InputFileParser parser = new InputFileParser();
		PricingStrategyInput result = null;
		try {
			result = parser.parse("nonexistentfile.txt");
			fail();
		} catch (InputFileParserException ifpe) {
			assertNull(result);
		}
	}
}
