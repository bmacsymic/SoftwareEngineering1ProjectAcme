package acme.persistence;

import java.util.ArrayList;

import acme.objects.Category;
import acme.objects.Item;
import acme.objects.ItemCategory;
import acme.objects.ShoppingCart;
import acme.objects.User;

public interface DataAccess {

	public abstract void open(String dbName);

	public abstract void close();
	
	public abstract void initialize();

	public abstract ArrayList<Item> getItems(Item item);

	public abstract ArrayList<User> getUsers(User user);

	public abstract ArrayList<Category> getCategories(Category category);

	public abstract ArrayList<ShoppingCart> getShoppingCarts(
			ShoppingCart shoppingCart);

	public abstract Item getItem(Item item);

	public abstract boolean insertItem(Item item);

	public abstract boolean deleteItem(Item item);

	public abstract boolean updateItem(Item currentItem, Item newItem);

	public abstract Category getCategory(Category category);

	public abstract boolean insertCategory(Category category);

	public abstract boolean deleteCategory(Category category);

	public abstract boolean updateCategory(Category currentCategory,
			Category newCategory);

	public abstract User getUser(User user);

	public abstract boolean insertUser(User user);

	public abstract boolean deleteUser(User user);

	public abstract boolean updateUser(User currentUser, User newUser);

	public abstract ArrayList<ItemCategory> getItemCategory(
			ItemCategory itemCategory);

	public abstract ArrayList<ItemCategory> getCategoryItem(
			ItemCategory itemCategory);

	public abstract boolean insertItemCategory(ItemCategory itemCategory);

	public abstract boolean updateItemCategory(ItemCategory currItemCategory,
			ItemCategory newItemCategory);

	public abstract boolean deleteItemCategory(ItemCategory itemCategory);

	public abstract ShoppingCart getShoppingCart(ShoppingCart shoppingCart);

	public abstract boolean insertShoppingCart(ShoppingCart shoppingCart);

	public abstract boolean deleteShoppingCart(ShoppingCart shoppingCart);

	public abstract boolean updateShoppingCart(ShoppingCart currShoppingCart,
			ShoppingCart newShoppingCart);
	
}