package tests.businessTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tests.persistence.DataAccessStub;
import acme.application.Main;
import acme.application.Services;
import acme.business.AccessItemCategory;
import acme.objects.ItemCategory;

public class AccessItemCategoryTest {

	@Before
	public void setUp() {
		Services.closeDataAccess();
		Services.createDataAccess(new DataAccessStub(Main.dbName));
	}

	@After
	public void tearDown() {
		Services.closeDataAccess();
	}

	@Test
	public void testInsertItemCategory() {
		AccessItemCategory AIC = new AccessItemCategory();
		
		ItemCategory ct1 = new ItemCategory();
		ItemCategory ct2 = new ItemCategory("item2", "category2");
		ItemCategory ct3 = new ItemCategory("item3", "category3");
		
		assertFalse(AIC.insertItemCategory(ct1));
		assertTrue(AIC.insertItemCategory(ct2));
		assertTrue(AIC.insertItemCategory(ct3));
		assertFalse(AIC.insertItemCategory(null));
		assertFalse(AIC.insertItemCategory(ct3));

		//test getItemCategory
		ArrayList<ItemCategory> category;
		assertNotNull(category = AIC.getItemCategory(new ItemCategory("item2", "category2")));
		assertEquals("category2", category.get(0).getCategoryName());
		assertEquals(category.size(), 1);
		
		assertNotNull(category = AIC.getItemCategory(ct3));
		assertEquals("category3", category.get(0).getCategoryName());
		assertEquals(category.size(), 1);
		
		assertTrue(AIC.insertItemCategory(new ItemCategory("item2", "test")));
		assertNotNull(category = AIC.getItemCategory(ct2));
		assertTrue(category.contains(ct2));
		assertTrue(category.contains(new ItemCategory("item2", "test")));
		assertEquals(2, category.size());
		
		assertNull(category = AIC.getItemCategory(new ItemCategory("unexistent", "unexistent")));
		
		assertNull(AIC.getItemCategory(null));

		//test getCategoryItem
		ArrayList<ItemCategory> item;
		assertNotNull(item = AIC.getCategoryItem(new ItemCategory("item2", "category2")));
		assertEquals("item2", item.get(0).getItemName());
		assertEquals(item.size(), 1);
		
		assertNotNull(item = AIC.getCategoryItem(ct3));
		assertEquals("item3", item.get(0).getItemName());
		assertEquals(item.size(), 1);
		
		assertTrue(AIC.insertItemCategory(new ItemCategory("item5", "test")));
		assertNotNull(item = AIC.getCategoryItem(new ItemCategory("item5", "test")));
		assertTrue(item.contains(new ItemCategory("item2", "test")));
		assertTrue(item.contains(new ItemCategory("item5", "test")));
		assertEquals(2, item.size());
		
		assertNull(item = AIC.getCategoryItem(new ItemCategory("unexistent", "unexistent")));
		
		assertNull(AIC.getCategoryItem(null));
				
		//test deleteItemCategory
		assertTrue(AIC.deleteItemCategory(new ItemCategory("item2", "test")));
		assertTrue(AIC.deleteItemCategory(new ItemCategory("item5", "test")));
		
		assertFalse(AIC.deleteItemCategory(new ItemCategory("unexistent", "unexistent")));
		assertFalse(AIC.deleteItemCategory(ct1));
		assertFalse(AIC.deleteItemCategory(ct1));
		assertTrue(AIC.deleteItemCategory(ct2));
		assertFalse(AIC.deleteItemCategory(ct2));
		assertFalse(AIC.deleteItemCategory(null));
		assertTrue(AIC.deleteItemCategory(ct3));
		assertFalse(AIC.deleteItemCategory(new ItemCategory("String name", "ItemCategory, ItemCategory")));
		assertFalse(AIC.deleteItemCategory(new ItemCategory("String name","String description")));

		System.out.println("Finished AccessItemsTest(1/2).");
	}


	@Test
	public void testUpdateItem() {
		AccessItemCategory AIC = new AccessItemCategory();
		
		ItemCategory ic = new ItemCategory("UpdateTest", "category");
		assertTrue(AIC.insertItemCategory(ic));
		
		ic.setCategoryName("Updated?");
		assertTrue(AIC.updateItemCategory(ic, ic));
		ArrayList<ItemCategory> category;
		assertNotNull(category = AIC.getItemCategory(ic));
		assertEquals("Updated?", category.get(0).getCategoryName());

		ItemCategory newItem = new ItemCategory("newItemC", "newItemC");
		assertTrue(AIC.insertItemCategory(newItem));
		assertTrue(AIC.updateItemCategory(ic, newItem));
		ArrayList<ItemCategory> category2;
		assertNull(category2 = AIC.getItemCategory(ic));
		assertNotNull(category2 = AIC.getItemCategory(newItem));
		assertEquals("newItemC", category2.get(0).getCategoryName());
		
		System.out.println("Finished AccessItemsTest(2/2).");
	}
}
