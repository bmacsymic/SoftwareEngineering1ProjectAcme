package tests.businessTests;

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
import acme.business.AccessCategories;
import acme.objects.Category;

public class AccessCategoriesTest {
	@BeforeClass
	public static void oneTimeSetUp() {
		Services.closeDataAccess();
		Services.createDataAccess(new DataAccessStub(Main.dbName));
	}

	@AfterClass
	public static void oneTimeTearDown() {
		Services.closeDataAccess();
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
