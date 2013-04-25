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
import acme.business.AccessShoppingCart;
import acme.objects.ShoppingCart;

public class AccessShoppingCartTest {
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
		AccessShoppingCart ASC = new AccessShoppingCart();
		ShoppingCart tempSC = new ShoppingCart(100);
		ShoppingCart tempSC2 = new ShoppingCart(2000);
		ShoppingCart tempSC3 = new ShoppingCart(5000);

		assertNotNull(ASC);
		assertNotNull(tempSC);
		assertNotNull(tempSC2);
		assertTrue(ASC.insertShoppingCart(tempSC));
		assertFalse(ASC.insertShoppingCart(tempSC));
		assertNotNull(ASC.getShoppingCart(tempSC));
		assertNull(ASC.getShoppingCart(tempSC2));
		assertTrue(ASC.insertShoppingCart(tempSC2));
		assertNotNull(ASC.getShoppingCart(tempSC2));
		assertNotNull(ASC.getSequential());
		assertFalse(ASC.updateShoppingCart(tempSC, tempSC2));
		assertTrue(ASC.updateShoppingCart(tempSC, tempSC3));
		assertFalse(ASC.deleteShoppingCart(tempSC));
		assertTrue(ASC.deleteShoppingCart(tempSC3));
		assertTrue(ASC.deleteShoppingCart(tempSC2));
		assertTrue(ASC.insertShoppingCart(tempSC));
		assertTrue(ASC.deleteShoppingCart(tempSC));
		assertNull(ASC.getSequential());

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
