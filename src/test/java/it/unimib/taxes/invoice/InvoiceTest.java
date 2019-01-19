package it.unimib.taxes.invoice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.unimib.taxes.item.Item;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class InvoiceTest {
		
	Item itemA = new Item("book", 12.49, false, true);
	Item itemB = new Item("music CD", 14.99, false, false);
	Item itemC = new Item("chocolate bar", 0.85, false, true);
	
	@Test
	public void testConstructorAndGetters() {
		
		Invoice invoice = new Invoice(1, "luca");
		invoice.addItem(itemA);
		invoice.addItem(itemB);
		invoice.addItem(itemC);
		assertEquals(invoice.getId(), 1);
		assertEquals(invoice.getCustomerName(), "luca");
		
		List<Item> items = new ArrayList<Item>();
		items.add(itemA);
		items.add(itemB);
		items.add(itemC);
		
		assertEquals(invoice.getItems(), items);
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyNameArgument() {
		
		Invoice invoice = new Invoice(1, "");
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullNameArgument() {
		
		Invoice invoice = new Invoice(1, null);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"-1", "-2147483648"})
	public void testIllegalPriceArgument(int id) {
		
		Invoice invoice = new Invoice(id, "luca");
		
	}
	
	@Test
	@Parameters({"0", "1", "2147483647"})
	public void testLegalPriceArgument(int id) {
		
		Invoice invoice = new Invoice(id, "luca");
		assertEquals(invoice.getId(), id);
		
	}
	
	@Test
	@Parameters({"a" , "niccolò" , "цлыовшщыд"})
	public void testLegalNameArgument(String name) {
		
		Invoice invoice = new Invoice(1, name);
		assertEquals(invoice.getCustomerName(), name);
		
	}
	
	@Test
	public void testEqualsAndHashCode() {

		Item itemD = new Item("imported bottle of perfume", 47.50, true, false);
		
		Invoice invoiceA = new Invoice(1, "luca");
		invoiceA.addItem(itemA);
		invoiceA.addItem(itemB);
		invoiceA.addItem(itemC);
		
		Invoice invoiceB = new Invoice(1, "luca");
		invoiceB.addItem(itemA);
		invoiceB.addItem(itemB);
		invoiceB.addItem(itemC);

		assertEquals(invoiceA, invoiceB);
		assertEquals(invoiceA.hashCode(), invoiceB.hashCode());
		
		// different id
		invoiceB = new Invoice(2, "luca");
		invoiceB.addItem(itemA);
		invoiceB.addItem(itemB);
		invoiceB.addItem(itemC);		
		assertNotEquals(invoiceA,invoiceB);	
		assertNotEquals(invoiceA.hashCode(),invoiceB.hashCode());	
		
		// different name
		invoiceB = new Invoice(1, "franco");
		invoiceB.addItem(itemA);
		invoiceB.addItem(itemB);
		invoiceB.addItem(itemC);		
		assertNotEquals(invoiceA,invoiceB);			
		assertNotEquals(invoiceA.hashCode(),invoiceB.hashCode());	
		
		//different items
		invoiceB = new Invoice(1, "luca");
		invoiceB.addItem(itemA);
		invoiceB.addItem(itemB);
		invoiceB.addItem(itemC);
		invoiceB.addItem(itemD);
		assertNotEquals(invoiceA,invoiceB);			
		assertNotEquals(invoiceA.hashCode(),invoiceB.hashCode());

		//everything different
		invoiceB = new Invoice(2, "franco");
		invoiceB.addItem(itemD);
		assertNotEquals(invoiceA,invoiceB);			
		assertNotEquals(invoiceA.hashCode(),invoiceB.hashCode());
		
	}

}
