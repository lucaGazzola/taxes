package it.unimib.taxes.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.unimib.taxes.item.Item;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ItemTest {
	
	private final double EPSILON = 0.000001;
	
	@Test
	public void testConstructorAndGetters() {
		
		Item item = new Item("War and Peace", 14.90, false, Type.BOOK);
		assertEquals(item.getName(), "War and Peace");
		assertEquals(item.getPrice(), 14.90, EPSILON);
		assertEquals(item.isImported(), false);
		assertEquals(item.getType(), Type.BOOK);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyNameArgument() {
		
		Item item = new Item("", 14.90, false, Type.BOOK);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullNameArgument() {
		
		Item item = new Item(null, 14.90, false, Type.BOOK);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({"0", "-1", "-1E308", "0.001"})
	public void testIllegalPriceArgument(double price) {
		
		Item item = new Item("War and Peace", price, false, Type.BOOK);
		
	}
	
	@Test
	@Parameters({"0.01", "1", "1E308"})
	public void testLegalPriceArgument(double price) {
		
		Item item = new Item("War and Peace", price, false, Type.BOOK);
		assertEquals(item.getPrice(), price, EPSILON);
		
	}
	
	@Test
	@Parameters({"a" , "città" , "fish & chips", "цлыовшщыд дыжфц"})
	public void testLegalNameArgument(String name) {
		
		Item item = new Item(name, 14.90, false, Type.BOOK);
		assertEquals(item.getName(), name);
		
	}
	
	@Test
	public void testEqualsAndHashCode() {

		Item itemA = new Item("War and Peace", 14.90, false, Type.BOOK);
		Item itemB = new Item("War and Peace", 14.90, false, Type.BOOK);
		assertEquals(itemA, itemB);
		assertEquals(itemA.hashCode(), itemB.hashCode());
		
		// different price
		itemB = new Item("War and Peace", 14.91, false, Type.BOOK);
		assertNotEquals(itemA,itemB);	
		assertNotEquals(itemA.hashCode(),itemB.hashCode());	
		
		// different name
		itemB = new Item("War and Peac", 14.90, false, Type.BOOK);
		assertNotEquals(itemA,itemB);
		assertNotEquals(itemA.hashCode(),itemB.hashCode());	
		
		//different type
		itemB = new Item("War and Peace", 14.90, false, Type.OTHER);
		assertNotEquals(itemA,itemB);
		assertNotEquals(itemA.hashCode(),itemB.hashCode());	
		
		//different import
		itemB = new Item("War and Peace", 14.90, true, Type.BOOK);
		assertNotEquals(itemA,itemB);
		assertNotEquals(itemA.hashCode(),itemB.hashCode());	

		//everything different
		itemB = new Item("Moby Dick", 17, true, Type.BOOK);
		assertNotEquals(itemA,itemB);
		assertNotEquals(itemA.hashCode(),itemB.hashCode());	
		
	}
	
	@Test
	public void testToString() {
		Item item = new Item("War and Peace", 14.90, false, Type.BOOK);
		assertEquals(item.toString(), "War and Peace, price: 14.90");
	}
	
}
