package tests.persistence;

import java.util.ArrayList;
import java.util.HashSet;

import acme.application.Main;
import acme.objects.Category;
import acme.objects.Item;
import acme.objects.ItemCategory;
import acme.objects.ShoppingCart;
import acme.objects.User;
import acme.persistence.DataAccess;

public class DataAccessStub implements DataAccess {
	private String dbName;
	private String dbType = "stub";
	private ArrayList<Item> items;
	private ArrayList<Category> categories;
	private ArrayList<User> users;
	private ArrayList<ItemCategory> itemCategories;
	private ArrayList<ShoppingCart> shoppingCarts;

	public DataAccessStub(String dbName) {
		this.dbName = dbName;
	}

	public DataAccessStub() {
		this(Main.dbName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#open(java.lang.String)
	 */
	@Override
	public void open(String dbName) {
		initialize();

		System.out.println("Opened " + dbType + " database " + dbName);
	}

	@Override
	public void initialize() {
		items = new ArrayList<Item>();
		categories = new ArrayList<Category>();
		users = new ArrayList<User>();
		itemCategories = new ArrayList<ItemCategory>();
		shoppingCarts = new ArrayList<ShoppingCart>();
	}
	
	public void insertDefaultValues() {
		Item item;
		Category category;
		User user;
		ItemCategory ic;
		ShoppingCart sc;

		item = new Item("name1", "description1", 10.00, 10, null);
		item.setFileName("images/wile_e_coyote-01.gif");
		items.add(item);

		item = new Item("name2", "description2", 10.00, 10, null);
		item.setFileName("images/wile_e2.jpg");
		items.add(item);

		item = new Item("name3", "description3", 10.00, 10, null);
		items.add(item);

		category = new Category("category1");
		categories.add(category);

		category = new Category("category2");
		categories.add(category);

		category = new Category("category3");
		categories.add(category);

		ic = new ItemCategory(items.get(0).getName(), categories.get(0)
				.getName());
		itemCategories.add(ic);

		ic = new ItemCategory(items.get(1).getName(), categories.get(1)
				.getName());
		itemCategories.add(ic);

		ic = new ItemCategory(items.get(2).getName(), categories.get(2)
				.getName());
		itemCategories.add(ic);

		user = new User("name1", "lastname1");
		sc = new ShoppingCart(user.getUserID());
		sc.addItem(items.get(0).getName(), 1);
		sc.addItem(items.get(1).getName(), 1);
		sc.addItem(items.get(2).getName(), 1);
		shoppingCarts.add(sc);
		users.add(user);

		user = new User("name2", "lastname2");
		sc = new ShoppingCart(user.getUserID());
		sc.addItem(items.get(0).getName(), 2);
		sc.addItem(items.get(1).getName(), 2);
		sc.addItem(items.get(2).getName(), 2);
		shoppingCarts.add(sc);
		users.add(user);

		user = new User("name3", "lastname3");
		sc = new ShoppingCart(user.getUserID());
		sc.addItem(items.get(0).getName(), 3);
		sc.addItem(items.get(1).getName(), 3);
		sc.addItem(items.get(2).getName(), 3);
		shoppingCarts.add(sc);
		users.add(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#close()
	 */
	@Override
	public void close() {
		System.out.println("Closed " + dbType + " database " + dbName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#getItemsSequential()
	 */
	@Override
	public ArrayList<Item> getItems(Item item) {
		return items;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#getUsersSequential()
	 */
	@Override
	public ArrayList<User> getUsers(User user) {
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#getCategoriesSequential()
	 */
	@Override
	public ArrayList<Category> getCategories(Category category) {
		return categories;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#getItem(acme.objects.Item)
	 */
	@Override
	public Item getItem(Item item) {
		Item result = null;

		if (item != null) {
			for (int index = 0; index < items.size(); index++) {
				if (items.get(index).equals(item))
					result = items.get(index);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#insertItem(acme.objects.Item)
	 */
	@Override
	public boolean insertItem(Item item) {
		boolean wasInserted = false;
		if (item != null && getItem(item) == null) {
			items.add(item);
			wasInserted = true;
		}
		return wasInserted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#deleteItem(acme.objects.Item)
	 */
	@Override
	public boolean deleteItem(Item item) {
		boolean wasRemoved = false;

		if (item != null) {
			wasRemoved = items.remove(item);
		}
		return wasRemoved;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#updateItem(acme.objects.Item,
	 * acme.objects.Item)
	 */
	@Override
	public boolean updateItem(Item currentItem, Item newItem) {
		boolean wasUpdated = false;

		if (currentItem != null && newItem != null) {
			for (int index = 0; index < items.size(); index++) {
				if (items.get(index).equals(currentItem)) {
					items.set(index, newItem);
					wasUpdated = true;
				}
			}
		}
		return wasUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#getCategory(acme.objects.Category)
	 */
	@Override
	public Category getCategory(Category category) {
		Category result = null;
		if (category != null) {
			for (int i = 0; i < categories.size(); i++) {
				if (categories.get(i).equals(category))
					result = categories.get(i);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#insertCategory(acme.objects.Category)
	 */
	@Override
	public boolean insertCategory(Category category) {
		boolean found = false;
		if (category != null && getCategory(category) == null) {
			categories.add(category);
			found = true;
		}
		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#deleteCategory(acme.objects.Category)
	 */
	@Override
	public boolean deleteCategory(Category category) {
		return categories.remove(category);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#updateCategory(acme.objects.Category,
	 * acme.objects.Category)
	 */
	@Override
	public boolean updateCategory(Category currentCategory, Category newCategory) {
		boolean wasUpdated = false;

		if (currentCategory != null && newCategory != null) {
			for (int index = 0; index < categories.size(); index++) {
				if (categories.get(index).equals(currentCategory)) {
					categories.set(index, newCategory);
					wasUpdated = true;
				}
			}
		}
		return wasUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#getUser(acme.objects.User)
	 */
	@Override
	public User getUser(User user) {
		User result = null;

		if (user != null) {
			for (int index = 0; index < users.size(); index++) {
				if (users.get(index).equals(user))
					result = users.get(index);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#insertUser(acme.objects.User)
	 */
	@Override
	public boolean insertUser(User user) {
		boolean found = false;
		if (user != null && getUser(user) == null) {
			users.add(user);
			found = true;
		}
		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#deleteUser(acme.objects.User)
	 */
	@Override
	public boolean deleteUser(User user) {
		return users.remove(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acme.persistence.DataAccess#updateUser(acme.objects.User,
	 * acme.objects.User)
	 */
	@Override
	public boolean updateUser(User currentUser, User newUser) {
		boolean wasUpdated = false;

		if (currentUser != null && newUser != null) {
			for (int index = 0; index < users.size(); index++) {
				if (users.get(index).equals(currentUser)) {
					users.set(index, newUser);
					wasUpdated = true;
				}
			}
		}
		return wasUpdated;
	}

	@Override
	public ArrayList<ItemCategory> getItemCategory(ItemCategory itemCategory) {
		ArrayList<ItemCategory> result = new ArrayList<ItemCategory>();

		for (ItemCategory ic : itemCategories) {
			if (ic.getItemName().equals(itemCategory.getItemName()))
				result.add(ic);
		}

		return result;
	}

	@Override
	public ArrayList<ItemCategory> getCategoryItem(ItemCategory itemCategory) {
		ArrayList<ItemCategory> result = new ArrayList<ItemCategory>();

		for (ItemCategory ic : itemCategories) {
			if (ic.getCategoryName().equals(itemCategory.getCategoryName()))
				result.add(ic);
		}

		return result;
	}

	@Override
	public boolean insertItemCategory(ItemCategory itemCategory) {
		boolean result = true;

		result &= itemCategories.add(itemCategory);

		HashSet<ItemCategory> h = new HashSet<ItemCategory>(itemCategories);
		
		if (h.size() < itemCategories.size())
			result = false;
		
		itemCategories.clear();
		result &= itemCategories.addAll(h);

		return result;
	}

	@Override
	public boolean updateItemCategory(ItemCategory currItemCategory,
			ItemCategory newItemCategory) {
		boolean result = true;

		result &= itemCategories.remove(currItemCategory);
		result &= itemCategories.add(newItemCategory);

		return result;
	}

	@Override
	public boolean deleteItemCategory(ItemCategory itemCategory) {
		return itemCategories.remove(itemCategory);
	}

	@Override
	public ArrayList<ShoppingCart> getShoppingCarts(ShoppingCart shoppingCart) {
		return shoppingCarts;
	}

	@Override
	public ShoppingCart getShoppingCart(ShoppingCart shoppingCart) {
		ShoppingCart result = null;

		if (shoppingCart != null) {
			for (int index = 0; index < shoppingCarts.size(); index++) {
				if (shoppingCarts.get(index).equals(shoppingCart))
					result = shoppingCarts.get(index);
			}
		}
		return result;
	}

	@Override
	public boolean insertShoppingCart(ShoppingCart shoppingCart) {
		boolean found = false;
		if (shoppingCart != null && getShoppingCart(shoppingCart) == null) {
			shoppingCarts.add(shoppingCart);
			found = true;
		}
		return found;
	}

	@Override
	public boolean deleteShoppingCart(ShoppingCart shoppingCart) {
		return shoppingCarts.remove(shoppingCart);
	}

	@Override
	public boolean updateShoppingCart(ShoppingCart currShoppingCart,
			ShoppingCart newShoppingCart) {
		int index = shoppingCarts.indexOf(currShoppingCart);
		boolean result = true;

		if (index != -1 && getShoppingCart(newShoppingCart) == null)
			shoppingCarts.set(index, newShoppingCart);
		else
			result = false;

		return result;
	}
}
