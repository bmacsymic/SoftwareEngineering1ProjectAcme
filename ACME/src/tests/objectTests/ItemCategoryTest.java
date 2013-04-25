package tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import acme.objects.ItemCategory;

public class ItemCategoryTest {

	@Test
	public void testItemCategoryCreation() {
		ItemCategory ic = new ItemCategory("itemName", "itemCategory");

		assertNotNull(ic);
		assertEquals(ic.getItemName(), "itemName");
		assertEquals(ic.getCategoryName(), "itemCategory");

		System.out.println("Finished ItemCategoryTest(1/2).");
	}

	@Test
	public void testEqualsObject() {
		ItemCategory ic1 = new ItemCategory("itemName1", "itemCategory1");
		ItemCategory ic2 = new ItemCategory("itemName2", "itemCategory2");
		ItemCategory ic3 = new ItemCategory("itemName3", "itemCategory3");
		ItemCategory ic4 = new ItemCategory("itemName4", "itemCategory4");

		assertTrue(ic1.equals(ic1));
		assertFalse(ic1.equals(ic2));
		assertFalse(ic2.equals(ic3));
		assertFalse(ic1.equals(ic4));
		assertFalse(ic1.equals(null));

		System.out.println("Finished ItemCategoryTest(2/2).");
	}

}
