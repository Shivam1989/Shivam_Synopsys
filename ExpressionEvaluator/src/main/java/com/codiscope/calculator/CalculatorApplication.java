package com.codiscope.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class CalculatorApplication{

	private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorApplication.class.getName());
	
	public static void main(String[] args) {
		
		String inputexpr = null;
		
		if(args.length > 0)  {
			
			inputexpr = args[0].replaceAll(" ", "");
			
			// minimum length for any expression is 8. Ex:- add(1,2)
			// maximum length we are allowing to solve a expression is 200 
			if(inputexpr.length() < 8 || inputexpr.length() > 200) {
				LOGGER.error("Length of input expression must be greater than 8 and less than 200");
				LOGGER.error("Input expression not valid");
				return;
			}
			
		}
		else
			System.out.println("Please enter input expression");	
		
		Calculator calculator = new Calculator();
		
		try {
			LOGGER.info("Input expression evaluating: " + inputexpr);
			
			float result = calculator.evaluateExpression(inputexpr);
			if(result != Integer.MAX_VALUE) {
				LOGGER.info("Result of the operation : " + result);
				System.out.println(result);
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Exception found: %1$s", e.getMessage()), e);
			e.printStackTrace();
		}
	}
}
