package tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import acme.objects.ShoppingCart;

public class ShoppingCartTest {

	@Test
	public void testShoppingCart() {
		ShoppingCart shoppingCart = new ShoppingCart(55555);

		assertNotNull(shoppingCart);
		assertEquals(shoppingCart.getUserId(), 55555);
		assertEquals(shoppingCart.getItems().size(), 0);

		System.out.println("Finished ShoppingCartTest(1/3).");
	}

	@Test
	public void testAddRemoveGetSizeFromList() {
		ShoppingCart sc = new ShoppingCart(55555);

		assertTrue(sc.addItem("Itemtest", 10));
		assertFalse(sc.getItems().isEmpty());
		assertEquals(sc.getItems().size(), 1);
		assertTrue(sc.getItems().get("Itemtest") == 10);
		assertTrue(sc.getItems().containsKey("Itemtest"));
		assertTrue(sc.getItems().containsValue(10));

		assertTrue(sc.addItem("test2", 50));
		assertFalse(sc.getItems().isEmpty());
		assertEquals(sc.getItems().size(), 2);
		assertTrue(sc.getItems().get("Itemtest") == 10);
		assertTrue(sc.getItems().get("test2") == 50);
		assertTrue(sc.getItems().containsKey("Itemtest"));
		assertTrue(sc.getItems().containsValue(10));
		assertTrue(sc.getItems().containsKey("test2"));
		assertTrue(sc.getItems().containsValue(50));

		assertTrue(sc.removeItem("Itemtest"));
		assertFalse(sc.getItems().isEmpty());
		assertEquals(sc.getItems().size(), 1);
		assertEquals(sc.getItems().get("Itemtest"), null);
		assertTrue(sc.getItems().get("test2") == 50);
		assertFalse(sc.getItems().containsKey("Itemtest"));
		assertFalse(sc.getItems().containsValue(10));
		assertTrue(sc.getItems().containsKey("test2"));
		assertTrue(sc.getItems().containsValue(50));

		assertTrue(sc.removeItem("test2"));
		assertTrue(sc.getItems().isEmpty());
		assertEquals(sc.getItems().size(), 0);
		assertEquals(sc.getItems().get("Itemtest"), null);
		assertEquals(sc.getItems().get("test2"), null);
		assertFalse(sc.getItems().containsKey("Itemtest"));
		assertFalse(sc.getItems().containsValue(10));
		assertFalse(sc.getItems().containsKey("test2"));
		assertFalse(sc.getItems().containsValue(50));

		System.out.println("Finished ShoppingCartTest(2/3).");
	}

	@Test
	public void testEqualsObject() {
		ShoppingCart sc1 = new ShoppingCart(100);
		ShoppingCart sc2 = new ShoppingCart(100);
		ShoppingCart sc3 = new ShoppingCart(101);
		ShoppingCart sc4 = new ShoppingCart(5000);

		assertTrue(sc1.equals(sc1));
		assertFalse(sc1.equals(sc4));
		assertTrue(sc1.equals(sc2));
		assertFalse(sc2.equals(sc3));
		assertFalse(sc1.equals(null));

		System.out.println("Finished ShoppingCartTest(3/3).");
	}

}
