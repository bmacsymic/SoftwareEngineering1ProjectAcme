package tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import acme.objects.User;

public class UserTest {
	@Test
	public void testUniqueUserID() {
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		User user4 = new User();

		assertTrue("user1.getUserID() = " + user1.getUserID()
				+ " and user2.getUserID() = " + user2.getUserID(),
				user1.getUserID() != user2.getUserID());
		assertTrue("user2.getUserID() = " + user2.getUserID()
				+ " and user3.getUserID() = " + user3.getUserID(),
				user2.getUserID() != user3.getUserID());
		assertTrue("user3.getUserID() = " + user3.getUserID()
				+ " and user4.getUserID() = " + user4.getUserID(),
				user3.getUserID() != user4.getUserID());
		assertTrue("user4.getUserID() = " + user4.getUserID()
				+ " and user1.getUserID() = " + user1.getUserID(),
				user4.getUserID() != user1.getUserID());
		assertFalse("user1.getUserID() = " + user1.getUserID()
				+ " and user1.getUserID() = " + user1.getUserID(),
				user1.getUserID() != user1.getUserID());

		System.out.println("Finished UserTest(1/7).");
	}

	@Test
	public void testCreationSupplied() {
		User user;
		user = new User("Admin");

		assertNotNull(user);
		assertTrue(user.getBirthdate().equals(""));
		assertTrue(user.getCity().equals(""));
		assertTrue(user.getCountry().equals(""));
		assertTrue(user.getEmail().equals(""));
		assertTrue(user.getFirstname().equals(""));
		assertTrue(user.getLastname().equals(""));
		assertTrue(user.getPhoneNumber().equals(""));
		assertTrue(user.getPostalCode().equals(""));
		assertTrue(user.getProvince().equals(""));
		assertTrue(user.getStreetAddress().equals(""));
		assertTrue(user.getType().equals("Admin"));

		System.out.println("Finished UserTest(2/7).");
	}

	@Test
	public void testCreationEmpty() {
		User user;
		user = new User();

		assertNotNull(user);
		assertTrue(user.getBirthdate().equals(""));
		assertTrue(user.getCity().equals(""));
		assertTrue(user.getCountry().equals(""));
		assertTrue(user.getEmail().equals(""));
		assertTrue(user.getFirstname().equals(""));
		assertTrue(user.getLastname().equals(""));
		assertTrue(user.getPhoneNumber().equals(""));
		assertTrue(user.getPostalCode().equals(""));
		assertTrue(user.getProvince().equals(""));
		assertTrue(user.getStreetAddress().equals(""));
		assertTrue(user.getType().equals(""));

		System.out.println("Finished UserTest(3/7).");
	}

	@Test
	public void testAllInformationCreation() {

		User user;
		user = new User("firstname", "lastname", "birthdate", "streetAddress",
				"city", "province", "postalCode", "country", "phoneNumber",
				"email", "type");

		assertNotNull(user);
		assertTrue(user.getBirthdate().equals("birthdate"));
		assertTrue(user.getCity().equals("city"));
		assertTrue(user.getCountry().equals("country"));
		assertTrue(user.getEmail().equals("email"));
		assertTrue(user.getFirstname().equals("firstname"));
		assertTrue(user.getLastname().equals("lastname"));
		assertTrue(user.getPhoneNumber().equals("phoneNumber"));
		assertTrue(user.getPostalCode().equals("postalCode"));
		assertTrue(user.getProvince().equals("province"));
		assertTrue(user.getStreetAddress().equals("streetAddress"));
		assertTrue(user.getType().equals("type"));

		System.out.println("Finished UserTest(4/7).");
	}

	@Test
	public void testEqualsMethod() {
		User user1 = new User("equalsTest1");
		User user2 = new User("equalsTest2");
		User user3 = new User();
		User user4 = new User("equalsTest1");

		assertTrue(user1.equals(user1));
		assertFalse(user1.equals(user4));
		assertFalse(user1.equals(user2));
		assertTrue(user3.equals(user3));
		assertFalse(user1.equals(null));

		System.out.println("Finished UserTest(5/7).");
	}

	@Test
	public void testUpdateUser() {
		User user;
		user = new User("firstname", "lastname", "birthdate", "streetAddress",
				"city", "province", "postalCode", "country", "phoneNumber",
				"email", "type");

		assertNotNull(user);
		assertEquals(user.getFirstname(), "firstname");
		assertEquals(user.getLastname(), "lastname");
		assertEquals(user.getBirthdate(), "birthdate");
		assertEquals(user.getStreetAddress(), "streetAddress");
		assertEquals(user.getCity(), "city");
		assertEquals(user.getProvince(), "province");
		assertEquals(user.getPostalCode(), "postalCode");
		assertEquals(user.getCountry(), "country");
		assertEquals(user.getPhoneNumber(), "phoneNumber");
		assertEquals(user.getEmail(), "email");
		assertEquals(user.getType(), "type");

		user.updateUser("test1", "test2", "test3", "test4", "test5", "test6",
				"test7", "test8", "test9", "test10", "test11");

		assertNotNull(user);
		assertEquals(user.getFirstname(), "test1");
		assertEquals(user.getLastname(), "test2");
		assertEquals(user.getBirthdate(), "test3");
		assertEquals(user.getStreetAddress(), "test4");
		assertEquals(user.getCity(), "test5");
		assertEquals(user.getProvince(), "test6");
		assertEquals(user.getPostalCode(), "test7");
		assertEquals(user.getCountry(), "test8");
		assertEquals(user.getPhoneNumber(), "test9");
		assertEquals(user.getEmail(), "test10");
		assertEquals(user.getType(), "test11");

		System.out.println("Finished UserTest(6/7).");
	}

	@Test
	public void testSetDefaultValues() {
		User user;
		user = new User("firstname", "lastname", "birthdate", "streetAddress",
				"city", "province", "postalCode", "country", "phoneNumber",
				"email", "type");

		user.setDefaultValues();

		assertNotNull(user);
		assertTrue(user.getBirthdate().equals(""));
		assertTrue(user.getCity().equals(""));
		assertTrue(user.getCountry().equals(""));
		assertTrue(user.getEmail().equals(""));
		assertTrue(user.getFirstname().equals(""));
		assertTrue(user.getLastname().equals(""));
		assertTrue(user.getPhoneNumber().equals(""));
		assertTrue(user.getPostalCode().equals(""));
		assertTrue(user.getProvince().equals(""));
		assertTrue(user.getStreetAddress().equals(""));
		assertTrue(user.getType().equals(""));

		System.out.println("Finished UserTest(7/7).");
	}
}
