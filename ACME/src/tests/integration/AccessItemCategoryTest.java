package tests.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import acme.application.Main;
import acme.application.Services;
import acme.business.AccessItems;
import acme.business.AccessCategories;
import acme.business.AccessItemCategory;
import acme.business.FileCopier;
import acme.objects.Item;
import acme.objects.Category;
import acme.objects.ItemCategory;
import acme.persistence.DataAccessObject;

public class AccessItemCategoryTest {
	@Before
	public void setUp() {
		Services.closeDataAccess();
		FileCopier.copyTextFile("database/acme.script", "database/curr.acme.script");
		FileCopier.copyTextFile("database/clean.acme.script", "database/acme.script");
		Services.createDataAccess(new DataAccessObject(Main.dbName));
	}

	@After
	public void tearDown() {
		Services.closeDataAccess();
		FileCopier.copyTextFile("database/curr.acme.script", "database/acme.script");
	}

	@Test
	public void testSupplied() {
		AccessItemCategory itemCategoryDAO = new AccessItemCategory();
		AccessItems itemDAO = new AccessItems();
		AccessCategories categoryDAO = new AccessCategories();
		
		Category c_a = new Category("Category a");
		Category c_b = new Category("Category b");
		categoryDAO.insertCategory(c_a);
		categoryDAO.insertCategory(c_b);
		
		Item i_a = new Item("Item a");
		Item i_b = new Item("Item b");
		itemDAO.insertItem(i_a);
		itemDAO.insertItem(i_b);
		
		ItemCategory ic1 = new ItemCategory(i_a.getName(), c_a.getName());
		ItemCategory ic2 = new ItemCategory(i_b.getName(), c_b.getName());
		ItemCategory ic3 = new ItemCategory(i_a.getName(), c_b.getName());
		
		assertTrue(itemCategoryDAO.insertItemCategory(ic1));
		assertEquals(itemCategoryDAO.getCategoryItem(ic1).size(), 1);
		assertEquals(itemCategoryDAO.getItemCategory(ic1).size(), 1);
		assertEquals(itemCategoryDAO.getCategoryItem(ic1).get(0), ic1);
		assertEquals(itemCategoryDAO.getItemCategory(ic1).get(0), ic1);
		
		assertTrue(itemCategoryDAO.updateItemCategory(ic1, ic2));
		assertFalse(itemCategoryDAO.insertItemCategory(ic2));
		assertFalse(itemCategoryDAO.deleteItemCategory(ic1));
		
		assertTrue(itemCategoryDAO.insertItemCategory(ic3));
		assertEquals(itemCategoryDAO.getItemCategory(ic3).size(), 1);
		assertEquals(itemCategoryDAO.getCategoryItem(ic3).size(), 2);
		assertTrue(itemCategoryDAO.getCategoryItem(ic3).contains(ic3));
		assertTrue(itemCategoryDAO.getCategoryItem(ic3).contains(ic2));
		assertTrue(itemCategoryDAO.getCategoryItem(ic2).contains(ic2));
		assertTrue(itemCategoryDAO.getCategoryItem(ic2).contains(ic3));
		
		assertTrue(itemCategoryDAO.deleteItemCategory(ic2));
		assertTrue(itemCategoryDAO.deleteItemCategory(ic3));

		System.out.println("Finished AccessItemCategoryTest(1/3).");
	}

	@Test
	public void testEmpty() {
		AccessItemCategory itemCategoryDAO = new AccessItemCategory();
		ItemCategory ic = new ItemCategory();

		assertNull(itemCategoryDAO.getItemCategory(ic));
		assertNull(itemCategoryDAO.getCategoryItem(ic));
		assertFalse(itemCategoryDAO.updateItemCategory(ic, ic));
		assertFalse(itemCategoryDAO.insertItemCategory(ic));
		assertFalse(itemCategoryDAO.deleteItemCategory(ic));

		System.out.println("Finished AccessItemCategoryTest(2/3).");
	}

	@Test
	public void testNull() {
		AccessItemCategory itemCategoryDAO = new AccessItemCategory();

		assertNull(itemCategoryDAO.getItemCategory(null));
		assertNull(itemCategoryDAO.getCategoryItem(null));
		assertFalse(itemCategoryDAO.updateItemCategory(null, null));
		assertFalse(itemCategoryDAO.insertItemCategory(null));
		assertFalse(itemCategoryDAO.deleteItemCategory(null));

		System.out.println("Finished AccessItemCategoryTest(3/3).");
	}
}
