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
import acme.business.AccessUsers;
import acme.business.FileCopier;
import acme.objects.User;
import acme.persistence.DataAccessObject;

public class AccessUsersTest {
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
		AccessUsers AU = new AccessUsers();
		User tempUser = new User(0);
		tempUser.setFirstname("First");
		tempUser.setLastname("Last");
		User tempUser2 = new User(1);
		tempUser2.setFirstname("Last");
		tempUser2.setLastname("First");

		assertNotNull(AU);
		assertTrue(AU.insertUser(tempUser));
		assertFalse(AU.insertUser(tempUser));
		assertNotNull(AU.getUser(tempUser));
		assertNotNull(AU.getSequential());
		assertTrue(AU.updateUser(tempUser, tempUser2));
		assertFalse(AU.deleteUser(tempUser2));
		assertTrue(AU.insertUser(tempUser2));
		assertTrue(AU.deleteUser(tempUser));
		assertTrue(AU.deleteUser(tempUser2));

		System.out.println("Finished AccessUsersTest(1/2).");
	}

	@Test
	public void testNull() {
		AccessUsers AU = null;
		assertNull(AU);

		AU = new AccessUsers();
		assertNotNull(AU);
		assertFalse(AU.insertUser(null));
		assertNull(AU.getUser(null));
		assertNull(AU.getSequential());
		assertFalse(AU.updateUser(null, null));
		assertFalse(AU.deleteUser(null));

		System.out.println("Finished AccessUsersTest(2/2).");
	}
}
