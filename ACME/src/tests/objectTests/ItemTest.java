package tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import acme.objects.Item;

public class ItemTest {
	@Test
	public void testCreationSupplied() {
		Item item = new Item("Anvil", "Commonly used in blacksmithing.", 99.99,
				100, null);

		assertNotNull(item);
		assertEquals(item.getName(), "Anvil");
		assertEquals(item.getDescription(), "Commonly used in blacksmithing.");
		assertEquals(item.getPrice(), 99.99, 0.0);
		assertEquals(item.getQuantity(), 100);
		assertNull(item.getFileName());

		System.out.println("Finished ItemTest(1/4).");
	}

	@Test
	public void testCreationEmpty() {
		Item item;
		item = new Item();

		assertNotNull(item);
		assertEquals(item.getName(), "");
		assertEquals(item.getDescription(), "");
		assertEquals(item.getPrice(), 00.00, 0.0);
		assertEquals(item.getQuantity(), 0);
		assertEquals(item.getFileName(), "");

		System.out.println("Finished ItemTest(2/4).");
	}

	@Test
	public void testEqualsMethod() {
		Item item1 = new Item("equalsTest1");
		Item item2 = new Item("equalsTest2");
		Item item3 = new Item("equalsTest3");
		Item item4 = new Item("equalsTest1");

		assertTrue(item1.equals(item1));
		assertTrue(item1.equals(item4));
		assertFalse(item1.equals(item2));
		assertFalse(item2.equals(item3));
		assertFalse(item1.equals(null));

		System.out.println("Finished ItemTest(3/4).");
	}

	@Test
	public void testContains() {
		Item item = new Item("name", "description", 10.00, 10, null);
		assertFalse(item.contains("string"));
		assertFalse(item.contains(""));
		assertFalse(item.contains(null));
		assertTrue(item.contains("name"));
		assertTrue(item.contains("description"));

		System.out.println("Finished ItemTest(4/4).");
	}
}
