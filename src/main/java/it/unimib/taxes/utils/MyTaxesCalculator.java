package it.unimib.taxes.utils;

import it.unimib.taxes.item.Item;
import it.unimib.taxes.item.Type;

public class MyTaxesCalculator implements TaxesCalculator{
	
	private static final double BASIC_TAX = 0.10;
	private static final double IMPORT_TAX = 0.05;
	
	public double calculateTaxes(Item item) {
				
		double tax = 0;

		if(item.isImported()) {
			
			tax += Math.ceil((item.getPrice() * IMPORT_TAX) * 20.0) / 20.0;
			
		}
		
		if(!isExempt(item)) {
			
			tax += Math.ceil((item.getPrice() * BASIC_TAX) * 20.0) / 20.0;

		}
		
		return tax;
	}

	private boolean isExempt(Item item) {
		return (item.getType() == Type.BOOK || item.getType() == Type.FOOD 
				|| item.getType() == Type.MEDICAL_PRODUCT);
	}
	

}
