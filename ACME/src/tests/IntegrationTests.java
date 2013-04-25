package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.integration.AccessCategoriesTest;
import tests.integration.AccessItemsTest;
import tests.integration.AccessItemCategoryTest;
import tests.integration.AccessShoppingCartTest;
import tests.integration.AccessUsersTest;

@RunWith(Suite.class)
@SuiteClasses({ AccessCategoriesTest.class, AccessItemsTest.class,
		AccessItemCategoryTest.class, AccessShoppingCartTest.class,
		AccessUsersTest.class })
public class IntegrationTests {
}
