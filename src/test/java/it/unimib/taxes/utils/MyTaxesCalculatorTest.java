package it.unimib.taxes.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unimib.taxes.item.Item;

public class MyTaxesCalculatorTest {
	
	private static final double EPSILON = 0.00001;
	
	// not imported, exempt
	Item book = new Item("book", 12.49, false, true);
	Item chocolateBar = new Item("chocolate bar", 0.85, false, true);
	Item headachePills = new Item("headache pills", 9.75, false, true);
	
	// imported, exempt
	Item importedChocolateA = new Item("imported box of chocolate", 10.00, true, true);
	Item importedChocolateB = new Item("imported box of chocolate", 11.25, true, true);

	// not imported, not exempt
	Item musicCD = new Item("music CD", 14.99, false, false);
	Item guitar = new Item("guitar", 749.65, false, false);
	Item perfume = new Item("perfume", 18.99, false, false);
	
	// imported, not exempt
	Item importedPerfumeA = new Item("imported perfume", 47.50, true, false);
	Item importedPerfumeB = new Item("imported perfume", 27.99, true, false);

	TaxesCalculator calculator = new MyTaxesCalculator();
	
	@Test
	public void testExemptNotImported() {
		
		assertEquals(calculator.calculateTaxes(book), 0, EPSILON);	
		assertEquals(calculator.calculateTaxes(chocolateBar), 0, EPSILON);	
		assertEquals(calculator.calculateTaxes(headachePills), 0, EPSILON);	

	}
	
	@Test
	public void testNotExemptNotImported() {
		
		assertEquals(calculator.calculateTaxes(musicCD), 1.5, EPSILON);
		assertEquals(calculator.calculateTaxes(guitar), 75, EPSILON);
		assertEquals(calculator.calculateTaxes(perfume), 1.9, EPSILON);	

		
	}
	
	@Test
	public void testExemptImported() {
		
		assertEquals(calculator.calculateTaxes(importedChocolateA), 0.5, EPSILON);
		assertEquals(calculator.calculateTaxes(importedChocolateB), 0.6, EPSILON);	
		
	}
	
	@Test
	public void testNotExemptImported() {
		
		assertEquals(calculator.calculateTaxes(importedPerfumeA), 7.15, EPSILON);	
		assertEquals(calculator.calculateTaxes(importedPerfumeB), 4.2, EPSILON);	

		
	}

}
