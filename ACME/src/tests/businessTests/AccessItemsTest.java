package tests.businessTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import tests.persistence.DataAccessStub;
import acme.application.Main;
import acme.application.Services;
import acme.business.AccessItems;
import acme.objects.Item;

public class AccessItemsTest {
	private AccessItems accessItems;
	Item item1;
	Item item2;
	Item item3;
	Item item4;

	@BeforeClass
	public static void oneTimeSetUp() {
		Services.closeDataAccess();
		Services.createDataAccess(new DataAccessStub(Main.dbName));
	}

	@AfterClass
	public static void oneTimeTearDown() {
		Services.closeDataAccess();
	}

	public AccessItemsTest() {
		accessItems = new AccessItems();
		item1 = new Item();
		item2 = new Item("item", "description", 0.00, 0, null);
		item3 = new Item("String name", "String description", 0.00, 1, null);
		item4 = new Item("String name", "String description", 0.00, 0, null);
	}

	@Test
	public void testGetSequential() {
		Item nextItem = accessItems.getSequential();
		int counter = 0;
		while (nextItem != null) {
			nextItem = accessItems.getSequential();
			counter++;
		}
		assertEquals(accessItems.getItemsSize(), counter);

		System.out.println("Finished AccessItemsTest(1/5).");
	}

	@Test
	public void testInsertItem() {
		assertTrue(accessItems.insertItem(item1));
		assertTrue(accessItems.insertItem(item2));
		assertTrue(accessItems.insertItem(item3));
		assertFalse(accessItems.insertItem(item4));
		assertFalse(accessItems.insertItem(null));
		assertFalse(accessItems.insertItem(item1));

		System.out.println("Finished AccessItemsTest(2/5).");
	}

	@Test
	public void testGetItem() {
		assertNotNull(accessItems.getItem(new Item("item", "description", 0.00,
				0, null)));
		assertNotNull(accessItems.getItem(new Item("item")));
		assertNotNull(accessItems.getItem(item1));
		assertNotNull(accessItems.getItem(item2));
		assertNotNull(accessItems.getItem(item3));

		// item4 contains an item with the same name as item3 which is in the
		// list, therefore it is found
		assertNotNull(accessItems.getItem(item4));
		assertNull(accessItems.getItem(new Item("unexistent", "unexistent",
				0.00, 1, null)));
		assertNull(accessItems.getItem(null));

		System.out.println("Finished AccessItemsTest(3/5).");
	}

	@Test
	public void testDeleteItem() {
		assertFalse(accessItems.deleteItem(new Item("unexistent", "unexistent",
				0.00, 1, null)));
		assertTrue(accessItems.deleteItem(item1));
		assertFalse(accessItems.deleteItem(item1));
		assertTrue(accessItems.deleteItem(item2));
		assertFalse(accessItems.deleteItem(item2));
		assertFalse(accessItems.deleteItem(null));

		// Even though item4 is not in the list, it has the same name as item 3
		// that is, and therefore deletes item3
		assertTrue(accessItems.deleteItem(item4));
		assertFalse(accessItems.deleteItem(new Item("String name")));
		assertFalse(accessItems.deleteItem(new Item("String name",
				"String description", 0.00, 1, null)));
		assertFalse(accessItems.deleteItem(item4));

		System.out.println("Finished AccessItemsTest(4/5).");
	}

	@Test
	public void testUpdateItem() {
		Item item = new Item("UpdateTest", "Description", 10.00, 19, null);
		assertTrue(accessItems.insertItem(item));
		item.setDescription("Updated?");
		assertTrue(accessItems.updateItem(item, item));
		assertEquals("Updated?", accessItems.getItem(item).getDescription());

		Item newItem = new Item("newItem", "newItem", 10.00, 19, null);
		assertTrue(accessItems.insertItem(newItem));
		assertTrue(accessItems.updateItem(item, newItem));

		System.out.println("Finished AccessItemsTest(5/5).");
	}
}
