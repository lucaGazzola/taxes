package it.unimib.taxes.invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unimib.taxes.item.Item;

public class Invoice {
	
	private final List<Item> items;
	private final int id;
	private final String customerName;
	
	public Invoice(int id, String customerName) {
		
		validateInput(customerName, id);
		
		this.items = new ArrayList<Item>();
		this.id = id;
		this.customerName = customerName;
		
	}
	
	private void validateInput(String customerName, int id) {
		
		if(customerName == null) {
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
	
	// TODO implement this
	public double getTotalPrice() throws Exception {
		throw(new Exception("not implemented"));		
	}
	
	// TODO implement this
	public double getTaxes() throws Exception {
		throw(new Exception("not implemented"));		
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

}
