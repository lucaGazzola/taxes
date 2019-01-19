package it.unimib.taxes.invoice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.unimib.taxes.item.Item;
import it.unimib.taxes.utils.MyTaxesCalculator;
import it.unimib.taxes.utils.TaxesCalculator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class InvoiceTest {
	
	private static final double EPSILON = 0.00001;
		
	// input 1
	Item book = new Item("book", 12.49, false, true);
	Item musicCD = new Item("music CD", 14.99, false, false);
	Item chocolateBar = new Item("chocolate bar", 0.85, false, true);

	// input 2
	Item importedChocolateA = new Item("imported box of chocolate", 10.00, true, true);
	Item importedPerfumeA = new Item("imported perfume", 47.50, true, false);

	// input 3
	Item importedPerfumeB = new Item("imported bottle of perfume", 27.99, true, false);
	Item perfume = new Item("bottle of perfume", 18.99, false, false);
	Item headachePills = new Item("packet of headache pills", 9.75, false, true);
	Item importedChocolateB = new Item("imported box of chocolate", 11.25, true, true);
	
	TaxesCalculator calculator = new MyTaxesCalculator();
	
	@Test
	public void testConstructorAndGetters() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(book);
		invoice.addItem(musicCD);
		invoice.addItem(chocolateBar);
		assertEquals(invoice.getId(), 1);
		assertEquals(invoice.getCustomerName(), "luca");
		
		List<Item> items = new ArrayList<Item>();
		items.add(book);
		items.add(musicCD);
		items.add(chocolateBar);
		
		assertEquals(invoice.getItems(), items);
		
		
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
		assertEquals(invoice.getId(), id);
		
	}
	
	@Test
	@Parameters({"a" , "niccolò" , "цлыовшщыд"})
	public void testLegalNameArgument(String name) {
		
		Invoice invoice = new Invoice(1, name, calculator);
		assertEquals(invoice.getCustomerName(), name);
		
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
		
		assertEquals(invoice.getTotalPrice(), 29.83, EPSILON);
		assertEquals(invoice.getTaxes(), 1.50, EPSILON);
		
	}
	
	@Test
	public void testSecondInput() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedChocolateA);
		invoice.addItem(importedPerfumeA);
		
		assertEquals(invoice.getTotalPrice(), 65.15, EPSILON);
		assertEquals(invoice.getTaxes(), 7.65, EPSILON);
		
	}
	
	@Test
	public void testThirdInput() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(perfume);
		invoice.addItem(headachePills);
		invoice.addItem(importedChocolateB);
		
		assertEquals(invoice.getTotalPrice(), 74.68, EPSILON);
		assertEquals(invoice.getTaxes(), 6.70, EPSILON);
		
	}
	
	@Test
	public void testDuplicateItem() {
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(perfume);
		invoice.addItem(headachePills);
		invoice.addItem(importedChocolateB);
		
		assertEquals(invoice.getTotalPrice(), 106.87, EPSILON);
		assertEquals(invoice.getTaxes(), 10.90, EPSILON);
		
	}
	
	@Test
	public void testToString() {
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(perfume);
		invoice.addItem(headachePills);
		invoice.addItem(importedChocolateB);
		
		assertEquals(invoice.toString(),"1 imported bottle of perfume: 32.19\n"
				+ "1 bottle of perfume: 20.89\n"
				+ "1 packet of headache pills: 9.75\n"
				+ "1 imported box of chocolate: 11.85\n"
				+ "Sales Taxes: 6.70\nTotal: 74.68");
	}
	
	@Test
	public void testToStringDuplicateItem() {
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(importedPerfumeB);
		invoice.addItem(perfume);
		invoice.addItem(headachePills);
		invoice.addItem(importedChocolateB);
		
		assertEquals(invoice.toString(),"2 imported bottle of perfume: 32.19\n"
				+ "1 bottle of perfume: 20.89\n"
				+ "1 packet of headache pills: 9.75\n"
				+ "1 imported box of chocolate: 11.85\n"
				+ "Sales Taxes: 10.90\nTotal: 106.87");
	}

}
