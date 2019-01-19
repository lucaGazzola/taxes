package it.unimib.taxes.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.unimib.taxes.item.Item;

public class InputParser {
	
	public static List<Item> parseItemList(String itemList){
		
		List<Item> items = new ArrayList<Item>();
		
		String lines[] = itemList.split("\\r?\\n");
		
		for(String line: lines) {
			
			String parts[] = line.split(" at ");
			String description[] = parts[0].split(" ");
			
			String name = "";
			for(String namePart: Arrays.copyOfRange(description, 1, description.length)) {
				name += namePart + " ";
			}
			name = name.substring(0, name.length()-1);
			
			int quantity = Integer.parseInt(description[0]);
			
			double price = Double.parseDouble(parts[1]);
			boolean imported = name.contains("imported");
			boolean exempt = name.contains("book") || parts[0].contains("chocolate")
					|| parts[0].contains("pills");
			
			for(int i = 0; i < quantity; i++) {
				items.add(new Item(name, price, imported, exempt));
			}
			
		}
		
		
		return items;
	}

}
