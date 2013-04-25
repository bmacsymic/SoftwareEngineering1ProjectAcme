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
import acme.business.AccessItems;
import acme.business.AccessShoppingCart;
import acme.business.AccessUsers;
import acme.business.FileCopier;
import acme.objects.Item;
import acme.objects.ShoppingCart;
import acme.objects.User;
import acme.persistence.DataAccessObject;

public class AccessShoppingCartTest {
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
		AccessShoppingCart shoppingCartDAO = new AccessShoppingCart();
		ShoppingCart sc = new ShoppingCart(100);
		
		assertNotNull(shoppingCartDAO);
		assertNotNull(sc);
		assertFalse(shoppingCartDAO.insertShoppingCart(sc));
		
		AccessItems itemDAO = new AccessItems();
		Item item = new Item("item", "description", 0.00, 0, null);
		Item item2 = new Item("String name", "String description", 0.00, 1, null);
		itemDAO.insertItem(item);
		itemDAO.insertItem(item2);
		
		sc.addItem(item.getName(), 1);
		sc.addItem(item2.getName(), 2);
		
		AccessUsers userDAO = new AccessUsers();
		User user = new User(100);
		userDAO.insertUser(user);
		
		assertTrue(shoppingCartDAO.insertShoppingCart(sc));
		
		ShoppingCart sc2 = new ShoppingCart(sc.getUserId());
		sc2.setItems(sc.getItems());
		Item item3 = new Item("item3", "description3", 2.00, 5, null);
		itemDAO.insertItem(item3);
		sc2.addItem(item3.getName(), 2);
		
		assertTrue(shoppingCartDAO.updateShoppingCart(sc, sc2));
		assertFalse(shoppingCartDAO.updateShoppingCart(sc, sc2));
		
		assertTrue(shoppingCartDAO.updateShoppingCart(sc2, sc));
		assertFalse(shoppingCartDAO.updateShoppingCart(sc2, sc));
		
		ShoppingCart sc3 = new ShoppingCart(sc.getUserId());
		sc3.addItem(item.getName(), 1);
		sc3.addItem(item2.getName(), 80);
		
		assertTrue(shoppingCartDAO.updateShoppingCart(sc, sc3));
		assertTrue(shoppingCartDAO.updateShoppingCart(sc, sc3));
		
		assertTrue(shoppingCartDAO.deleteShoppingCart(sc3));
		assertFalse(shoppingCartDAO.deleteShoppingCart(sc3));

		System.out.println("Finished AccessShoppingCartTest(1/3).");
	}

	@Test
	public void testEmpty() {
		AccessShoppingCart ASC = new AccessShoppingCart();
		ShoppingCart tempSC = new ShoppingCart(50);

		assertNull(ASC.getSequential());
		assertNull(ASC.getShoppingCart(tempSC));
		assertFalse(ASC.updateShoppingCart(tempSC, tempSC));
		assertFalse(ASC.deleteShoppingCart(tempSC));
		assertNull(ASC.getSequential());

		System.out.println("Finished AccessShoppingCartTest(2/3).");
	}

	@Test
	public void testNull() {
		AccessShoppingCart ASC = new AccessShoppingCart();

		assertNull(ASC.getSequential());
		assertNull(ASC.getShoppingCart(null));
		assertFalse(ASC.insertShoppingCart(null));
		assertFalse(ASC.updateShoppingCart(null, null));
		assertFalse(ASC.deleteShoppingCart(null));
		assertNull(ASC.getSequential());

		System.out.println("Finished AccessShoppingCartTest(3/3).");
	}
}
