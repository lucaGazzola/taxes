package it.unimib.taxes.invoice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import it.unimib.taxes.item.Item;
import it.unimib.taxes.utils.TaxesCalculator;

public class Invoice {
	
	private final List<Item> items;
	private final int id;
	private final String customerName;
	private final TaxesCalculator calculator;
	
	public Invoice(int id, String customerName, TaxesCalculator calculator) {
		
		validateInput(customerName, id, calculator);
		
		this.items = new ArrayList<Item>();
		this.id = id;
		this.customerName = customerName;
		this.calculator = calculator;
		
	}
	
	private void validateInput(String customerName, int id, TaxesCalculator calculator) {
		
		if(customerName == null) {
			throw(new IllegalArgumentException());
		}
		
		if(calculator == null) {
			throw(new IllegalArgumentException());
		}
		
		if(customerName.equals("")) {
			throw(new IllegalArgumentException());
		}
		
		if(id < 0) {
			throw(new IllegalArgumentException());
		}
	}
	
	public void addItem(Item item) {
		
		items.add(item);
		
	}
	
	public void addItems(List<Item> itemList) {
		
		for(Item item : itemList) {
			items.add(item);
		}
		
	}
	
	public boolean removeItem(Item item) {
		
		if(items.contains(item)) {
			items.remove(item);
			return true;
		}
		
		return false;
		
	}
	
	public List<Item> getItems(){
		return items;
	}
	
	public int getId(){
		return id;
	}
	
	public String getCustomerName(){
		return customerName;
	}
	
	public double getTotalPrice() {
		
		double total = 0;
		
		for(Item item : items) {
			total += item.getPrice() + calculator.calculateTaxes(item);
		}
		
		return total;
		
	}
	
	public double getTaxes() {
		
		double total = 0;
		
		for(Item item : items) {
			total += calculator.calculateTaxes(item);
		}
		
		return total;	
	
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o == this) {
			return true;
		}
		
		if(!(o instanceof Invoice)) {
			return false;
		}
		
		Invoice other = (Invoice) o;
		
		if(!(other.getCustomerName().equals(this.customerName))) {
			return false;
		}
		
		if(!(other.getItems().equals(items))) {
			return false;
		}
		
		if(other.getId() != this.id) {
			return false;
		}
		
		return true;
			
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.customerName, this.id, this.items);
	}
	
	@Override
	public String toString() {
		
		String invoice = "";
		
		if(items.isEmpty()) {
			return "This invoice contains no items.";
		}
		
		Set<Item> itemSet = new LinkedHashSet<Item>(items);
		
		for(Item item: itemSet) {
			
			double fullPrice = item.getPrice() + calculator.calculateTaxes(item);
			
			invoice += Collections.frequency(items, item);
			invoice += " ";
			invoice += item.getName();
			invoice += ": ";
			invoice += String.format("%.2f", fullPrice);
			invoice += "\n";
			
		}
		
		invoice += "Sales Taxes: "+String.format("%.2f", getTaxes())+"\n";
		invoice += "Total: "+String.format("%.2f", getTotalPrice());
		return invoice;
		
	}

}
