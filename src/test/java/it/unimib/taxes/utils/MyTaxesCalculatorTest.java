package it.unimib.taxes.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unimib.taxes.item.Item;
import it.unimib.taxes.item.Type;

public class MyTaxesCalculatorTest {
	
	private static final double EPSILON = 0.00001;
	
	// not imported, exempt
	Item book = new Item("book", 12.49, false, Type.BOOK);
	Item chocolateBar = new Item("chocolate bar", 0.85, false, Type.FOOD);
	Item headachePills = new Item("headache pills", 9.75, false, Type.MEDICAL_PRODUCT);
	
	// imported, exempt
	Item importedChocolateA = new Item("imported box of chocolate", 10.00, true, Type.FOOD);
	Item importedChocolateB = new Item("imported box of chocolate", 11.25, true, Type.FOOD);

	// not imported, not exempt
	Item musicCD = new Item("music CD", 14.99, false, Type.OTHER);
	Item guitar = new Item("guitar", 749.65, false, Type.OTHER);
	Item perfume = new Item("perfume", 18.99, false, Type.OTHER);
	
	// imported, not exempt
	Item importedPerfumeA = new Item("imported perfume", 47.50, true, Type.OTHER);
	Item importedPerfumeB = new Item("imported perfume", 27.99, true, Type.OTHER);

	TaxesCalculator calculator = new MyTaxesCalculator();
	
	@Test
	public void testExemptNotImported() {
		
		assertEquals(0, calculator.calculateTaxes(book), EPSILON);	
		assertEquals(0, calculator.calculateTaxes(chocolateBar), EPSILON);	
		assertEquals(0, calculator.calculateTaxes(headachePills), EPSILON);	

	}
	
	@Test
	public void testNotExemptNotImported() {
		
		assertEquals(1.5, calculator.calculateTaxes(musicCD), EPSILON);
		assertEquals(75, calculator.calculateTaxes(guitar), EPSILON);
		assertEquals(1.9, calculator.calculateTaxes(perfume), EPSILON);	

	}
	
	@Test
	public void testExemptImported() {
		
		assertEquals(0.5, calculator.calculateTaxes(importedChocolateA), EPSILON);
		assertEquals(0.6, calculator.calculateTaxes(importedChocolateB), EPSILON);	
		
	}
	
	@Test
	public void testNotExemptImported() {
		
		assertEquals(7.15, calculator.calculateTaxes(importedPerfumeA), EPSILON);	
		assertEquals(4.2, calculator.calculateTaxes(importedPerfumeB), EPSILON);	
	
	}
	
	@Test
	public void testBigPrices() {
		
		Item galaxy = new Item("imported galaxy", 1E300, true, Type.OTHER);
		Item planet = new Item("planet", 10000000000000.00, false, Type.OTHER);
		Item ocean = new Item("ocean", 1E250, true, Type.FOOD);

		assertEquals(1.5e299, calculator.calculateTaxes(galaxy), EPSILON);	
		assertEquals(1000000000000.00, calculator.calculateTaxes(planet), EPSILON);	
		assertEquals(5e248, calculator.calculateTaxes(ocean), EPSILON);	
	
	}
	
	@Test
	public void testSmallPrices() {
		
		Item stone = new Item("stone", 0.05, false, Type.OTHER);
		Item sheet = new Item("imported sheet", 0.01, true, Type.OTHER);
		Item crumb = new Item("crumb", 0.01, false, Type.FOOD);

		assertEquals(0.05, calculator.calculateTaxes(stone), EPSILON);	
		assertEquals(0.1, calculator.calculateTaxes(sheet), EPSILON);	
		assertEquals(0, calculator.calculateTaxes(crumb), EPSILON);		
		
	}

}
