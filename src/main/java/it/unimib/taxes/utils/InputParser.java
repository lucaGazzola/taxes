package it.unimib.taxes.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.unimib.taxes.item.Item;
import it.unimib.taxes.item.Type;

public class InputParser {
	
	public static List<Item> parseItemList(String itemList){
		
		List<Item> items = new ArrayList<Item>();
		
		if(itemList == "") {
			throw(new IllegalArgumentException("Empty item list!"));
		}
		
		String lines[] = itemList.split("\\r?\\n");
		
		for(String line: lines) {
			
			Type itemType = Type.OTHER;
			
			String parts[] = line.split(" at ");
			String description[] = parts[0].split(" ");
			
			String name = "";
			for(String namePart: Arrays.copyOfRange(description, 1, description.length)) {
				name += namePart + " ";
			}
			name = name.substring(0, name.length()-1);
			
			int quantity = Integer.parseInt(description[0]);
			
			if(!(quantity > 0)) {
				throw(new IllegalArgumentException("Item quantities must be positive!"));
			}
			
			double price = Double.parseDouble(parts[1]);
			boolean imported = name.contains("imported");
			
			if(name.contains("book")) {
				itemType = Type.BOOK;
			}
			if(name.contains("chocolate")) {
				itemType = Type.FOOD;
			}
			if(name.contains("pills")) {
				itemType = Type.MEDICAL_PRODUCT;
			}
			
			for(int i = 0; i < quantity; i++) {
				items.add(new Item(name, price, imported, itemType));
			}
			
		}
		
		
		return items;
	}

}
