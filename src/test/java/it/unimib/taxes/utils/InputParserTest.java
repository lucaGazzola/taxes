package it.unimib.taxes.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import it.unimib.taxes.item.Item;
import it.unimib.taxes.item.Type;

import org.junit.Test;

public class InputParserTest {
	
	@Test
	public void testFirstInput() {
		
		String firstInput = "1 book at 12.49\n"
				+ "1 music CD at 14.99\n"
				+ "1 chocolate bar at 0.85\n";
		
		List<Item> items = InputParser.parseItemList(firstInput);
		
		Item book = new Item("book", 12.49, false, Type.BOOK);
		Item musicCD = new Item("music CD", 14.99, false, Type.OTHER);
		Item chocolateBar = new Item("chocolate bar", 0.85, false, Type.FOOD);
		
		List<Item> itemsToCheck = new ArrayList<Item>();
		itemsToCheck.add(book);
		itemsToCheck.add(musicCD);
		itemsToCheck.add(chocolateBar);
		
		assertEquals(itemsToCheck, items);
	}
	
	@Test
	public void testSecondInput() {
		
		String firstInput = "1 imported box of chocolates at 10.00\n"
				+ "1 imported perfume at 47.50\n";
		
		List<Item> items = InputParser.parseItemList(firstInput);
		Item importedChocolateA = new Item("imported box of chocolates", 10.00, true, Type.FOOD);
		Item importedPerfumeA = new Item("imported perfume", 47.50, true, Type.OTHER);
		
		List<Item> itemsToCheck = new ArrayList<Item>();
		itemsToCheck.add(importedChocolateA);
		itemsToCheck.add(importedPerfumeA);
		
		assertEquals(itemsToCheck, items);
	}
	
	@Test
	public void testThirdInput() {
		
		String firstInput = "1 imported bottle of perfume at 27.99\n"
				+ "1 bottle of perfume at 18.99\n"
				+ "1 packet of headache pills at 9.75\n"
				+ "1 imported box of chocolates at 11.25";
		
		List<Item> items = InputParser.parseItemList(firstInput);
		
		Item importedPerfumeB = new Item("imported bottle of perfume", 27.99, true, Type.OTHER);
		Item perfume = new Item("bottle of perfume", 18.99, false, Type.OTHER);
		Item headachePills = new Item("packet of headache pills", 9.75, false, Type.MEDICAL_PRODUCT);
		Item importedChocolateB = new Item("imported box of chocolates", 11.25, true, Type.FOOD);
		
		List<Item> itemsToCheck = new ArrayList<Item>();
		itemsToCheck.add(importedPerfumeB);
		itemsToCheck.add(perfume);
		itemsToCheck.add(headachePills);
		itemsToCheck.add(importedChocolateB);

		assertEquals(itemsToCheck, items);
	}
	
	@Test
	public void testBigPrices() {
		
		String firstInput = "1 guitar at 10500.00\n"
				+ "1 car at 74000.50\n"
				+ "1 imported galaxy at 1E300\n";
		
		List<Item> items = InputParser.parseItemList(firstInput);
		
		Item guitar = new Item("guitar", 10500.00, false, Type.OTHER);
		Item car = new Item("car", 74000.50, false, Type.OTHER);
		Item galaxy = new Item("imported galaxy", 1E300, true, Type.OTHER);
		
		List<Item> itemsToCheck = new ArrayList<Item>();
		itemsToCheck.add(guitar);
		itemsToCheck.add(car);
		itemsToCheck.add(galaxy);
		
		assertEquals(itemsToCheck, items);
	}
	
	@Test
	public void testItemNames() {
		
		String firstInput = "1 a at 20.00\n"
				+ "1 città at 10000000.00\n"
				+ "1 шоколад at 2.99\n";
		
		List<Item> items = InputParser.parseItemList(firstInput);
		
		Item a = new Item("a", 20.00, false, Type.OTHER);
		Item citta = new Item("città", 10000000.00, false, Type.OTHER);
		Item chocolate = new Item("шоколад", 2.99, false, Type.OTHER);
		
		List<Item> itemsToCheck = new ArrayList<Item>();
		itemsToCheck.add(a);
		itemsToCheck.add(citta);
		itemsToCheck.add(chocolate);
		
		assertEquals(itemsToCheck, items);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyString() {
		
		InputParser.parseItemList("");
		
	}
	
	@Test
	public void testDuplicateItem() {
		
		String firstInput = "2 book at 12.49\n"
				+ "1 music CD at 14.99\n"
				+ "1 chocolate bar at 0.85\n";
		List<Item> items = InputParser.parseItemList(firstInput);
		
		Item book = new Item("book", 12.49, false, Type.BOOK);
		Item musicCD = new Item("music CD", 14.99, false, Type.OTHER);
		Item chocolateBar = new Item("chocolate bar", 0.85, false, Type.FOOD);
		
		List<Item> itemsToCheck = new ArrayList<Item>();
		itemsToCheck.add(book);
		itemsToCheck.add(book);
		itemsToCheck.add(musicCD);
		itemsToCheck.add(chocolateBar);
		
		assertEquals(itemsToCheck, items);
		
	}

}
