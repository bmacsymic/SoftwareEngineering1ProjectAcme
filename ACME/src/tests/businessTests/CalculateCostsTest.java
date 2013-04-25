package tests.businessTests;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import tests.persistence.DataAccessStub;

import acme.application.Main;
import acme.application.Services;
import acme.business.AccessItems;
import acme.business.CalculateCosts;
import acme.objects.Item;
import acme.objects.ShoppingCart;

public class CalculateCostsTest {
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
	public void testGetTotalCost() {
		ShoppingCart sc = new ShoppingCart(0);
		
		//test the empty case
		assertEquals(0.0, CalculateCosts.getTotalCost(null), 0);
		assertEquals(0.0, CalculateCosts.getTotalCost(sc), 0);
		
		AccessItems itemDAO = new AccessItems();
		HashMap<String, Integer> items = new HashMap<String, Integer>();
		Item curr;
		
		for (int i = 0; i < 10; ++i)
		{
			curr = new Item("Item " + String.valueOf(i));
			curr.setPrice(10.00);
			itemDAO.insertItem(curr);
			items.put(curr.getName(), 10);
		}
		sc.setItems(items);

		assertEquals(1120.0, CalculateCosts.getTotalCost(sc), 0);

		System.out.println("Finished CalculateCosts Test(1/3).");
	}

	@Test
	public void testCalculateTaxes() {

		// test for null
		assertEquals(0, CalculateCosts.calculateTaxes(0), 0);
		assertEquals(11.2, CalculateCosts.calculateTaxes(10), 0);
		assertEquals(28, CalculateCosts.calculateTaxes(25), 0);
		assertEquals(333.76, CalculateCosts.calculateTaxes(298), 0);
		assertEquals(1998.06, CalculateCosts.calculateTaxes(1783.98), 0);

		System.out.println("Finished CalculateCosts Test(2/3).");
	}

	@Test
	public void testRoundDouble() {
		assertEquals(0, CalculateCosts.roundDouble(0), 0);
		assertEquals(10, CalculateCosts.roundDouble(10), 0);
		assertEquals(25.99, CalculateCosts.roundDouble(25.988888889), 0);
		assertEquals(299, CalculateCosts.roundDouble(299.001010199), 0);
		assertEquals(300, CalculateCosts.roundDouble(299.995555599), 0);

		System.out.println("Finished CalculateCosts Test(3/3).");
	}
}
