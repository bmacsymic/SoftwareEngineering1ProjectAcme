package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.businessTests.AccessCategoriesTest;
import tests.businessTests.AccessItemCategoryTest;
import tests.businessTests.AccessItemsTest;
import tests.businessTests.AccessShoppingCartTest;
import tests.businessTests.AccessUsersTest;
import tests.businessTests.CalculateCostsTest;
import tests.businessTests.FileCopierTest;
import tests.objectTests.CategoryTest;
import tests.objectTests.ItemCategoryTest;
import tests.objectTests.ItemTest;
import tests.objectTests.ShoppingCartTest;
import tests.objectTests.UserTest;

@RunWith(Suite.class)
@SuiteClasses({ CategoryTest.class, ItemCategoryTest.class, ItemTest.class,
		ShoppingCartTest.class, UserTest.class, AccessCategoriesTest.class,
		AccessItemsTest.class, AccessItemCategoryTest.class,
		AccessShoppingCartTest.class, AccessUsersTest.class,
		CalculateCostsTest.class, FileCopierTest.class })
public class UnitTests {
}
