package tests.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import acme.application.Main;
import acme.application.Services;
import acme.business.AccessCategories;
import acme.business.FileCopier;
import acme.objects.Category;
import acme.persistence.DataAccessObject;

public class AccessCategoriesTest {
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
		AccessCategories AC = new AccessCategories();
		Category tempCategory = new Category("Explosives");
		Category tempCategory2 = new Category("ReallyBigExplosives");

		assertNotNull(AC);
		assertTrue(AC.insertCategory(tempCategory));
		assertFalse(AC.insertCategory(tempCategory));
		assertNotNull(AC.getCategory(tempCategory));
		assertNotNull(AC.getSequential());
		assertTrue(AC.updateCategory(tempCategory, tempCategory2));
		assertFalse(AC.deleteCategory(tempCategory));
		assertTrue(AC.insertCategory(tempCategory));
		assertTrue(AC.deleteCategory(tempCategory));

		System.out.println("Finished AccessCategoriesTest(1/3).");
	}

	@Test
	public void testEmpty() {
		AccessCategories AC = new AccessCategories();
		Category tempCategory = new Category("Explosives");

		assertNull(AC.getCategory(tempCategory));
		assertFalse(AC.updateCategory(tempCategory, tempCategory));
		assertFalse(AC.deleteCategory(tempCategory));

		System.out.println("Finished AccessCategoriesTest(2/3).");
	}

	@Test
	public void testNull() {
		AccessCategories AC = new AccessCategories();

		assertNull(AC.getCategory(null));
		assertFalse(AC.insertCategory(null));
		assertFalse(AC.updateCategory(null, null));
		assertFalse(AC.deleteCategory(null));

		System.out.println("Finished AccessCategoriesTest(3/3).");
	}
}
