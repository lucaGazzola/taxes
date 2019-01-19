package it.unimib.taxes.item;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
	
	private final String name;
	private final double price;
	private final boolean imported;
	private final boolean exempt;
	
	public Item(String name, double price, boolean imported, boolean exempt) {
		
		validateInput(name, price);
		
		this.name = name;
		this.price = price;
		this.imported = imported;
		this.exempt = exempt;
		
	}
	
	private void validateInput(String name, double price) {
		if(name == null) {
			throw(new IllegalArgumentException());
		}
		
		if(name.equals("")) {
			throw(new IllegalArgumentException());
		}
		
		if(!(price > 0) || BigDecimal.valueOf(price).scale() > 2) {
			throw(new IllegalArgumentException());
		}
	}
	
	public double getPrice(){
		return price;
	}
	
	public boolean isImported() {
		return this.imported;
	}
	
	public boolean isExempt() {
		return this.exempt;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o == this) {
			return true;
		}
		
		if(!(o instanceof Item)) {
			return false;
		}
		
		Item other = (Item) o;
		
		if(!(other.getName().equals(this.name))) {
			return false;
		}
		
		if(other.getPrice() != price) {
			return false;
		}
		
		if(other.isExempt() != this.exempt) {
			return false;
		}
		
		if(other.isImported() != this.imported) {
			return false;
		}
		
		return true;
			
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.price, this.imported, this.exempt);
	}
	
	@Override
	public String toString() {
		return this.name+", price: "+String.format("%.2f", price);
	}

}
