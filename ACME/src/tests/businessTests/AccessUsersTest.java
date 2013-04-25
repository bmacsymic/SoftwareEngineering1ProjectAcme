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
import acme.business.AccessUsers;
import acme.objects.User;

public class AccessUsersTest {
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
		AccessUsers AU = new AccessUsers();
		User tempUser = new User("First", "Last");
		User tempUser2 = new User("Last", "First");

		assertNotNull(AU);
		assertTrue(AU.insertUser(tempUser));
		assertFalse(AU.insertUser(tempUser));
		assertNotNull(AU.getUser(tempUser));
		assertNotNull(AU.getSequential());
		assertTrue(AU.updateUser(tempUser, tempUser2));
		assertFalse(AU.deleteUser(tempUser));
		assertTrue(AU.insertUser(tempUser));
		assertTrue(AU.deleteUser(tempUser));

		System.out.println("Finished AccessUsersTest(1/3).");
	}

	@Test
	public void testEmpty() {
		AccessUsers AU = new AccessUsers();
		User tempUser = new User();
		User tempUser2 = new User();

		assertNotNull(AU);
		assertTrue(AU.insertUser(tempUser));
		assertFalse(AU.insertUser(tempUser));
		assertNotNull(AU.getUser(tempUser));
		assertNotNull(AU.getSequential());
		assertTrue(AU.updateUser(tempUser, tempUser2));
		assertFalse(AU.updateUser(tempUser, null));
		assertFalse(AU.updateUser(null, tempUser2));
		assertFalse(AU.deleteUser(tempUser));
		assertTrue(AU.insertUser(tempUser));
		assertTrue(AU.deleteUser(tempUser));

		System.out.println("Finished AccessUsersTest(2/3).");
	}

	@Test
	public void testNull() {
		AccessUsers AU = null;
		assertNull(AU);

		AU = new AccessUsers();
		assertNotNull(AU);
		assertFalse(AU.insertUser(null));
		assertNull(AU.getUser(null));
		assertNotNull(AU.getSequential());
		assertFalse(AU.updateUser(null, null));
		assertFalse(AU.deleteUser(null));

		System.out.println("Finished AccessUsersTest(3/3).");
	}
}
