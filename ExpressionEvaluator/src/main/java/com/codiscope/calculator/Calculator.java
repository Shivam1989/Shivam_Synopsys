package com.codiscope.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class.getName());
	
	private Pattern pattern = Pattern.compile("\\d+");
	private Map<Object, Float> operations = new HashMap<>();

	/* This function evaluates expression based on the keywords
	   add, subtract, multiply, divide and let.
	   Map stores Key(Expression) ,Value(Evaluated input expression value ) 
	*/
	private float evaluate(Object expList) {

		if (expList instanceof String) {
			
			if (pattern.matcher(expList.toString()).matches())
				return Integer.parseInt(expList.toString());
			
			else
				return operations.get(expList.toString());
		} 
		else {
			
			ArrayList<?> expressionList = (ArrayList<?>) expList;

			String operator = expressionList.get(0).toString();
			Object exp = expressionList.get(1);

			if (operator.equalsIgnoreCase("start"))
				return evaluate(exp);

			else if (operator.equalsIgnoreCase("add")) {

				LOGGER.info("addition operation");
				return evaluate(exp) + evaluate(expressionList.get(2));				
			} else if (operator.equalsIgnoreCase("sub")) {

				LOGGER.info("subtraction operation");
				return evaluate(exp) - evaluate(expressionList.get(2));
			} else if (operator.equalsIgnoreCase("mult")) {

				LOGGER.info("multiplication operation");
				return evaluate(exp) * evaluate(expressionList.get(2));
			} else if (operator.equalsIgnoreCase("div")) {
				
				float divisor = evaluate(expressionList.get(2));
				
				// Checking divide by zero exception
				if(divisor == 0.0)
					throw new ArithmeticException("divide by zero exception");
				
				LOGGER.info("division operation");
				return evaluate(exp) / divisor;
			} else if (operator.equalsIgnoreCase("let")) {

				LOGGER.info("let operation");
				operations.put(exp, evaluate(expressionList.get(2)));
				return evaluate(expressionList.get(3));
			} else {

				LOGGER.error("Invalid operation");
				return Integer.MAX_VALUE;
			}
		}
	}

	 /*  This function uses '(' & ')' brackets as delimiter to split expression.
	     It create a Object array having expression as first input and its starting index
	 	 as second input 
	 */
	private Object[] parseExpression(String expression, String operation, int index) {
		
		ArrayList<Object> expList = new ArrayList<Object>();
		
		expList.add(operation);
		String temp = "";
		
		try {
			while (true) {
				if(expression.length() <= index)
					break;
				
				char delimcheck = expression.charAt(index);				
				if (delimcheck == '(') {
					Object[] returned = parseExpression(expression, temp, index + 1);
					index = (Integer) returned[1];
					expList.add(returned[0]);
					temp = "";
					
					if (operation.equalsIgnoreCase("start"))
						break;
					
				} else if (delimcheck == ')') {
					if (temp.length() > 0)
						expList.add(temp);
					break;
					
				} else if (delimcheck == ',') {
					if (temp.length() > 0)
						expList.add(temp);
					temp = "";
				} else {
					temp += expression.charAt(index);
				}
				++index;
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Exception found: %1$s", e.getMessage()), e);
			e.printStackTrace();
		}

		return new Object[] { expList, index };
	}

	//This function replaces the spaces.It uses parseExpression and evaluate function to parse and evaluate expression
	public float evaluateExpression(String expression) {
		LOGGER.info("Operation is started");				
		return evaluate(parseExpression(expression.replaceAll(" ", ""), "start", 0)[0]);
	}
}
