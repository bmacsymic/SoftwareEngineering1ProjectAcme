package tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import acme.objects.Category;

public class CategoryTest {
	@Test
	public void testCreationSupplied() {
		Category category;

		category = new Category("Explosives");

		assertNotNull(category);
		assertEquals(category.getName(), "Explosives");

		System.out.println("Finished CategoryTest(1/3).");
	}

	@Test
	public void testCreationEmpty() {
		Category category;
		category = new Category();

		assertNotNull(category);
		assertEquals(category.getName(), "");

		System.out.println("Finished CategoryTest(2/3).");
	}

	@Test
	public void testEqualsMethod() {
		Category category1 = new Category("equalsTest1");
		Category category2 = new Category("equalsTest2");
		Category category3 = new Category("equalsTest3");
		Category category4 = new Category("equalsTest1");

		assertTrue(category1.equals(category1));
		assertTrue(category1.equals(category4));
		assertFalse(category1.equals(category2));
		assertFalse(category2.equals(category3));
		assertFalse(category1.equals(null));

		System.out.println("Finished CategoryTest(3/3).");
	}
}
