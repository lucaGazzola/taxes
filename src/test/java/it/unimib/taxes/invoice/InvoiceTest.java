package it.unimib.taxes.invoice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.*;

import it.unimib.taxes.item.Item;
import it.unimib.taxes.item.Type;
import it.unimib.taxes.utils.TaxesCalculator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class InvoiceTest {
	
	private static final double EPSILON = 0.00001;
		
	// input 1
	Item book = new Item("book", 12.49, false, Type.BOOK);
	Item musicCD = new Item("music CD", 14.99, false, Type.OTHER);
	Item chocolateBar = new Item("chocolate bar", 0.85, false, Type.FOOD);

	// input 2
	Item importedChocolateA = new Item("imported box of chocolates", 10.00, true, Type.FOOD);
	Item importedPerfumeA = new Item("imported perfume", 47.50, true, Type.OTHER);

	// input 3
	Item importedPerfumeB = new Item("imported bottle of perfume", 27.99, true, Type.OTHER);
	Item perfume = new Item("bottle of perfume", 18.99, false, Type.OTHER);
	Item headachePills = new Item("packet of headache pills", 9.75, false, Type.MEDICAL_PRODUCT);
	Item importedChocolateB = new Item("imported box of chocolates", 11.25, true, Type.OTHER);
	
	@Mock
	TaxesCalculator calculator;
	
	@Rule 
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Test
	public void testConstructorAndGetters() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(book);
		invoice.addItem(musicCD);
		invoice.addItem(chocolateBar);
		assertEquals(1, invoice.getId());
		assertEquals("luca", invoice.getCustomerName());
		
		List<Item> items = new ArrayList<Item>();
		items.add(book);
		items.add(musicCD);
		items.add(chocolateBar);
		
		assertEquals(items, invoice.getItems());
		
		
	}
	
	@Test
	public void testAddItemList() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		
		List<Item> items = new ArrayList<Item>();
		items.add(book);
		items.add(musicCD);
		items.add(chocolateBar);
		
		invoice.addItems(items);
		
		assertEquals(items, invoice.getItems());
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyNameArgument() {
		
		Invoice invoice = new Invoice(1, "", calculator);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullNameArgument() {
		
		Invoice invoice = new Invoice(1, null, calculator);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"-1", "-2147483648"})
	public void testIllegalPriceArgument(int id) {
		
		Invoice invoice = new Invoice(id, "luca", calculator);
		
	}
	
	@Test
	@Parameters({"0", "1", "2147483647"})
	public void testLegalPriceArgument(int id) {
		
		Invoice invoice = new Invoice(id, "luca", calculator);
		assertEquals(id, invoice.getId());
		
	}
	
	@Test
	@Parameters({"a" , "niccolò" , "цлыовшщыд"})
	public void testLegalNameArgument(String name) {
		
		Invoice invoice = new Invoice(1, name, calculator);
		assertEquals(name, invoice.getCustomerName());
		
	}
	
	@Test
	public void testEqualsAndHashCode() {
		
		Invoice invoiceA = new Invoice(1, "luca", calculator);
		invoiceA.addItem(book);
		invoiceA.addItem(musicCD);
		invoiceA.addItem(chocolateBar);
		
		Invoice invoiceB = new Invoice(1, "luca", calculator);
		invoiceB.addItem(book);
		invoiceB.addItem(musicCD);
		invoiceB.addItem(chocolateBar);

		assertEquals(invoiceA, invoiceB);
		assertEquals(invoiceA.hashCode(), invoiceB.hashCode());
		
		// different id
		invoiceB = new Invoice(2, "luca", calculator);
		invoiceB.addItem(book);
		invoiceB.addItem(musicCD);
		invoiceB.addItem(chocolateBar);		
		assertNotEquals(invoiceA,invoiceB);	
		assertNotEquals(invoiceA.hashCode(),invoiceB.hashCode());	
		
		// different name
		invoiceB = new Invoice(1, "franco", calculator);
		invoiceB.addItem(book);
		invoiceB.addItem(musicCD);
		invoiceB.addItem(chocolateBar);		
		assertNotEquals(invoiceA,invoiceB);			
		assertNotEquals(invoiceA.hashCode(),invoiceB.hashCode());	
		
		//different items
		invoiceB = new Invoice(1, "luca", calculator);
		invoiceB.addItem(book);
		invoiceB.addItem(musicCD);
		invoiceB.addItem(chocolateBar);
		invoiceB.addItem(importedChocolateA);
		assertNotEquals(invoiceA,invoiceB);			
		assertNotEquals(invoiceA.hashCode(),invoiceB.hashCode());

		//everything different
		invoiceB = new Invoice(2, "franco", calculator);
		invoiceB.addItem(importedChocolateA);
		assertNotEquals(invoiceA,invoiceB);			
		assertNotEquals(invoiceA.hashCode(),invoiceB.hashCode());
		
	}
	
	@Test
	public void testFirstInput() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(book);
		invoice.addItem(musicCD);
		invoice.addItem(chocolateBar);
		
		when(calculator.calculateTaxes(book)).thenReturn(0.0);
		when(calculator.calculateTaxes(musicCD)).thenReturn(1.5);
		when(calculator.calculateTaxes(chocolateBar)).thenReturn(0.0);

		
		assertEquals(29.83, invoice.getTotalPrice(), EPSILON);
		assertEquals(1.50, invoice.getTaxes(), EPSILON);
		
	}
	
	@Test
	public void testSecondInput() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedChocolateA);
		invoice.addItem(importedPerfumeA);
		
		when(calculator.calculateTaxes(importedChocolateA)).thenReturn(0.5);
		when(calculator.calculateTaxes(importedPerfumeA)).thenReturn(7.15);
		
		assertEquals(65.15, invoice.getTotalPrice(), EPSILON);
		assertEquals(7.65, invoice.getTaxes(), EPSILON);
		
	}
	
	@Test
	public void testThirdInput() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(perfume);
		invoice.addItem(headachePills);
		invoice.addItem(importedChocolateB);
		
		when(calculator.calculateTaxes(importedPerfumeB)).thenReturn(4.2);
		when(calculator.calculateTaxes(perfume)).thenReturn(1.9);
		when(calculator.calculateTaxes(headachePills)).thenReturn(0.0);
		when(calculator.calculateTaxes(importedChocolateB)).thenReturn(0.6);

		
		assertEquals(74.68, invoice.getTotalPrice(), EPSILON);
		assertEquals(6.70, invoice.getTaxes(), EPSILON);
		
	}
	
	@Test
	public void testDuplicateItem() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(perfume);
		invoice.addItem(headachePills);
		invoice.addItem(importedChocolateB);
		
		when(calculator.calculateTaxes(importedPerfumeB)).thenReturn(4.2);
		when(calculator.calculateTaxes(perfume)).thenReturn(1.9);
		when(calculator.calculateTaxes(headachePills)).thenReturn(0.0);
		when(calculator.calculateTaxes(importedChocolateB)).thenReturn(0.6);
		
		assertEquals(106.87, invoice.getTotalPrice(), EPSILON);
		assertEquals(10.90, invoice.getTaxes(), EPSILON);
		
	}
	
	@Test
	public void testToString() {
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(perfume);
		invoice.addItem(headachePills);
		invoice.addItem(importedChocolateB);
		
		when(calculator.calculateTaxes(importedPerfumeB)).thenReturn(4.2);
		when(calculator.calculateTaxes(perfume)).thenReturn(1.9);
		when(calculator.calculateTaxes(headachePills)).thenReturn(0.0);
		when(calculator.calculateTaxes(importedChocolateB)).thenReturn(0.6);
		
		assertEquals("1 imported bottle of perfume: 32.19\n"
				+ "1 bottle of perfume: 20.89\n"
				+ "1 packet of headache pills: 9.75\n"
				+ "1 imported box of chocolates: 11.85\n"
				+ "Sales Taxes: 6.70\nTotal: 74.68", invoice.toString());
	}
	
	@Test
	public void testToStringDuplicateItem() {
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(perfume);
		invoice.addItem(headachePills);
		invoice.addItem(importedChocolateB);
		
		when(calculator.calculateTaxes(importedPerfumeB)).thenReturn(4.2);
		when(calculator.calculateTaxes(perfume)).thenReturn(1.9);
		when(calculator.calculateTaxes(headachePills)).thenReturn(0.0);
		when(calculator.calculateTaxes(importedChocolateB)).thenReturn(0.6);
		
		assertEquals("2 imported bottle of perfume: 32.19\n"
				+ "1 bottle of perfume: 20.89\n"
				+ "1 packet of headache pills: 9.75\n"
				+ "1 imported box of chocolates: 11.85\n"
				+ "Sales Taxes: 10.90\nTotal: 106.87", invoice.toString());
	}

}
