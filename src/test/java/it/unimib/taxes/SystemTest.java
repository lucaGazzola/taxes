package it.unimib.taxes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unimib.taxes.invoice.Invoice;
import it.unimib.taxes.utils.InputParser;
import it.unimib.taxes.utils.MyTaxesCalculator;
import it.unimib.taxes.utils.TaxesCalculator;

public class SystemTest {
	
	TaxesCalculator calculator = new MyTaxesCalculator();
	
	@Test
	public void testFirstInput() {
		
		String firstInput = "1 book at 12.49\n"
				+ "1 music CD at 14.99\n"
				+ "1 chocolate bar at 0.85";
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItems(InputParser.parseItemList(firstInput));
		
		assertEquals("1 book: 12.49\n"
				+ "1 music CD: 16.49\n"
				+ "1 chocolate bar: 0.85\n"
				+ "Sales Taxes: 1.50\nTotal: 29.83", invoice.toString());
		
	}
	
	@Test
	public void testSecondInput() {
		
		String secondInput = "1 imported box of chocolates at 10.00\n"
				+ "1 imported bottle of perfume at 47.50";
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItems(InputParser.parseItemList(secondInput));
		
		assertEquals("1 imported box of chocolates: 10.50\n"
				+ "1 imported bottle of perfume: 54.65\n"
				+ "Sales Taxes: 7.65\nTotal: 65.15", invoice.toString());
		
	}
	
	@Test
	public void testThirdInput() {
		
		String thirdInput = "1 imported bottle of perfume at 27.99\n"
				+ "1 bottle of perfume at 18.99\n"
				+ "1 packet of headache pills at 9.75\n"
				+ "1 imported box of chocolates at 11.25";
		
		Invoice invoice = new Invoice(1, "luca", calculator);
		invoice.addItems(InputParser.parseItemList(thirdInput));
		
		assertEquals("1 imported bottle of perfume: 32.19\n"
				+ "1 bottle of perfume: 20.89\n"
				+ "1 packet of headache pills: 9.75\n"
				+ "1 imported box of chocolates: 11.85\n"
				+ "Sales Taxes: 6.70\nTotal: 74.68", invoice.toString());
		
	}
	
}
