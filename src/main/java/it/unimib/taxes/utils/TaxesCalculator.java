package it.unimib.taxes.utils;

import it.unimib.taxes.item.Item;

public class TaxesCalculator {
	
	private static final double BASIC_TAX = 0.10;
	private static final double IMPORT_TAX = 0.05;

	
	public static double calculateTaxes(Item item) {
				
		double tax = 0;

		if(item.isImported()) {
			
			tax += Math.ceil((item.getPrice() * IMPORT_TAX) * 20.0) / 20.0;
			System.out.println(tax);
			
		}
		
		if(!item.isExempt()) {
			
			tax += Math.ceil((item.getPrice() * BASIC_TAX) * 20.0) / 20.0;
			System.out.println(tax);

		}
		
		return tax;
	}

}
