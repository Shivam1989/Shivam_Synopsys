package com.codiscope.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codiscope.calculator.Calculator;

public class CalculatorTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorTest.class.getName());
	
	private static Calculator calculatorTest;

	@BeforeClass
	public static void setUp() throws Exception {
		LOGGER.debug("JUnit Test case started");
		LOGGER.debug("Initializing the setup");
		LOGGER.debug("Calculator class object has been created");
		calculatorTest = new Calculator();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		LOGGER.debug("Calculator class object has been assigned null");
		LOGGER.debug("JUnit Test case is over");		
		calculatorTest = null;
	}
	

	@Test(expected = RuntimeException.class)
	public void testNull() throws Exception {
		LOGGER.debug("Testing empty string");
		assertEquals("Empty String", calculatorTest.evaluateExpression(""));
	}
	
	@Test
	public void testAdd() {
		LOGGER.debug("Testing addition of 1 and 2");
		assertEquals(3.0, calculatorTest.evaluateExpression("add(1,2)"), 0.0);
	}
	
	@Test
	public void testSubtract() {
		LOGGER.debug("Testing addition of 2 and 1");
		assertEquals(1.0, calculatorTest.evaluateExpression("sub(2,1)"), 0.0);
	}
	
	@Test
	public void testMultiplication() {
		LOGGER.debug("Testing multiplication of 2 and 2");
		assertEquals(4.0, calculatorTest.evaluateExpression("mult(2,2)"), 0.0);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testDivision() {
		LOGGER.debug("Testing divide by zero");
		assertEquals("error", calculatorTest.evaluateExpression("div(2,0)"));
	}
	
	@Test
	public void testAddAndMultiplication() {
		LOGGER.debug("Testing addition of 1 with multiplication of 2 and 3");
		assertEquals(7.0, calculatorTest.evaluateExpression("add(1, mult(2, 3))"), 0.0);
	}
	
	@Test
	public void testLetOperation() {
		LOGGER.debug("Testing nested let operation");
		assertEquals(55.0, calculatorTest.evaluateExpression("let(a, 5, let(b, mult(a, 10), add(b, a)))"), 0.0);
	}
	
	@Test(expected = NullPointerException.class)
	public void testSubNegativeNumber() {
		LOGGER.debug("Testing negative number");
		assertEquals("error", calculatorTest.evaluateExpression("sub(1, 2))"));

	@Test(expected = NullPointerException.class)
	public void testNegativeNumber() {
		LOGGER.debug("Testing negative number");
		assertEquals("error", calculatorTest.evaluateExpression("mult(-1, 2))"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddNegativeNumber() {
		LOGGER.debug("Testing negative number");
		assertEquals("error", calculatorTest.evaluateExpression("add(-1, -2))"));
	}
}
