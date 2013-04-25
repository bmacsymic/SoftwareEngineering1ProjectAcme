package acme.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map.Entry;

import acme.objects.Category;
import acme.objects.Item;
import acme.objects.ItemCategory;
import acme.objects.ShoppingCart;
import acme.objects.User;

public class DataAccessObject implements DataAccess {
	private String dbName;
	private String dbType;

	private Connection connection;
	private String cmdString;

	private ArrayList<Item> items;
	private ArrayList<Category> categories;
	private ArrayList<User> users;

	public DataAccessObject(String dbName) {
		this.dbName = dbName;
	}
	
	@Override
	public void open(String dbName) {
		String url;

		try {
			dbType = "HSQL";
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:database/" + dbName; // stored on disk mode
			connection = DriverManager.getConnection(url, "SA", "");
		} catch (Exception e) {
			processSQLError(e);
		}

		System.out.println("Opened " + dbType + " database " + dbName);
	}

	@Override
	public void close() {
		try { // commit all changes to the database
			cmdString = "SHUTDOWN COMPACT";

			Statement s = connection.createStatement();
			s.execute(cmdString);
			s.close();

			connection.close();
		} catch (Exception e) {
			processSQLError(e);
		}
		System.out.println("Closed " + dbType + " database " + dbName);
	}

	@Override
	public void initialize() {
		try{
			Statement s = connection.createStatement();
			
			cmdString = "DELETE FROM Users";
			s.addBatch(cmdString);
			
			cmdString = "DELETE FROM Items";
			s.addBatch(cmdString);
			
			cmdString = "DELETE FROM Categories";
			s.addBatch(cmdString);
			
			s.executeBatch();
		} catch (Exception e) {
			processSQLError(e);
		}
	}
	
	@Override
	public ArrayList<Item> getItems(Item item) {
		Item retVal;
		String name = null;
		String description = null;
		String imageFile = null;
		double price = 0.0;
		int quantity = 0;

		items = new ArrayList<Item>();

		try {
			cmdString = "SELECT i.Name, i.Description, i.Image_File, i.Price, i.Quantity";
			cmdString += " FROM Items i";
			if (item != null)
				cmdString += " WHERE i.Name = '" + item.getName() + "'";

			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(cmdString);

			while (rs.next()) {
				name = rs.getString(1);
				description = rs.getString(2);
				imageFile = rs.getString(3);
				price = rs.getDouble(4);
				quantity = rs.getInt(5);

				retVal = new Item(name, description, price, quantity, imageFile);
				items.add(retVal);
			}

			s.close();
		} catch (Exception e) {
			processSQLError(e);
		}

		return items;
	}

	@Override
	public ArrayList<Category> getCategories(Category category) {
		Category retVal;
		String name;

		categories = new ArrayList<Category>();
		
		try {
			cmdString = "SELECT c.name";
			cmdString += " FROM Categories c";
			if (category != null)
				cmdString += " WHERE c.name = '" + category.getName() + "'";

			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(cmdString);

			while (rs.next()) {
				name = rs.getString("name");

				retVal = new Category(name);
				categories.add(retVal);
			}

			s.close();
		} catch (Exception e) {
			processSQLError(e);
		}

		return categories;
	}

	@Override
	public ArrayList<User> getUsers(User user) {
		User retVal;
		int id = 0;
		String type = null;
		String fname = null;
		String lname = null;
		String bday = null;
		String addr = null;
		String city = null;
		String prov = null;
		String post = null;
		String ctry = null;
		String phone = null;
		String email = null;

		users = new ArrayList<User>();

		try {
			cmdString = "SELECT u.Id, u.Type, u.FName, u.LName, u.Birthdate, u.Address, u.City, u.Province, u.Postal_Code, u.Country, u.Phone_Num, u.Email";
			cmdString += " FROM Users u";
			if (user != null)
				cmdString += " WHERE u.Id = '" + user.getUserID() + "'";

			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(cmdString);

			while (rs.next()) {
				id = rs.getInt(1);
				type = rs.getString(2);
				fname = rs.getString(3);
				lname = rs.getString(4);
				bday = rs.getString(5);
				addr = rs.getString(6);
				city = rs.getString(7);
				prov = rs.getString(8);
				post = rs.getString(9);
				ctry = rs.getString(10);
				phone = rs.getString(11);
				email = rs.getString(12);

				retVal = new User(id);
				retVal.setType(type);
				retVal.setFirstname(fname);
				retVal.setLastname(lname);
				retVal.setBirthdate(bday);
				retVal.setStreetAddress(addr);
				retVal.setCity(city);
				retVal.setProvince(prov);
				retVal.setPostalCode(post);
				retVal.setCountry(ctry);
				retVal.setPhoneNumber(phone);
				retVal.setEmail(email);

				users.add(retVal);
			}

			s.close();
		} catch (Exception e) {
			processSQLError(e);
		}

		return users;
	}

	@Override
	public Item getItem(Item item) {
		ArrayList<Item> items = getItems(item);

		if (items.isEmpty()) {
			return null;
		} else {
			return items.get(0);
		}
	}

	@Override
	public boolean insertItem(Item item) {
		String values;
		String errStr;
		int updateCount;
		boolean result = true;

		try {
			values = "NULL, '" + item.getName() + "'";
			values += ", " + item.getPrice();
			values += ", " + item.getQuantity();
			values += ", '" + item.getFileName() + "'";
			values += ", '" + item.getDescription() + "'";
			cmdString = "INSERT INTO Items VALUES(" + values + ")";

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public boolean deleteItem(Item item) {
		String values;
		String errStr;
		boolean result = true;
		int updateCount;

		try {
			values = item.getName();
			cmdString = "DELETE FROM Items WHERE Name='" + values + "'";

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public boolean updateItem(Item currentItem, Item newItem) {
		String errStr;
		boolean result = true;
		int updateCount;

		try {
			cmdString = "UPDATE Items SET";
			cmdString += " Name='" + newItem.getName() + "'";
			cmdString += ", Price=" + newItem.getPrice();
			cmdString += ", Quantity=" + newItem.getQuantity();
			cmdString += ", Image_File='" + newItem.getFileName() + "'";
			cmdString += ", Description='" + newItem.getDescription() + "'";
			cmdString += " WHERE Name='" + currentItem.getName() + "'";

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public Category getCategory(Category category) {
		// TODO Auto-generated method stub
		ArrayList<Category> categories = getCategories(category);

		if (categories.isEmpty()) {
			return null;
		} else {
			return categories.get(0);
		}
	}

	@Override
	public boolean insertCategory(Category category) {
		String values;
		String errStr;
		int updateCount;
		boolean result = true;

		try {
			values = "NULL, '" + category.getName() + "'";
			cmdString = "INSERT INTO Categories VALUES(" + values + ")";

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public boolean deleteCategory(Category category) {
		String values;
		String errStr;
		boolean result = true;
		int updateCount;

		try {
			values = category.getName();
			cmdString = "DELETE FROM Categories WHERE Name='" + values + "'";

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public boolean updateCategory(Category currentCategory, Category newCategory) {
		String errStr;
		boolean result = true;
		int updateCount;

		try {
			cmdString = "UPDATE Categories SET";
			cmdString += " Name='" + newCategory.getName() + "'";
			cmdString += " WHERE Name='" + currentCategory.getName() + "'";

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public User getUser(User user) {
		ArrayList<User> users = getUsers(user);

		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public boolean insertUser(User user) {
		String values;
		String errStr;
		int updateCount;
		boolean result = true;

		try {
			values = String.valueOf(user.getUserID());
			values += ", '" + user.getType() + "'";
			values += ", '" + user.getFirstname() + "'";
			values += ", '" + user.getLastname() + "'";
			values += ", '" + user.getBirthdate() + "'";
			values += ", '" + user.getStreetAddress() + "'";
			values += ", '" + user.getCity() + "'";
			values += ", '" + user.getProvince() + "'";
			values += ", '" + user.getPostalCode() + "'";
			values += ", '" + user.getCountry() + "'";
			values += ", '" + user.getPhoneNumber() + "'";
			values += ", '" + user.getEmail() + "'";

			cmdString = "INSERT INTO Users VALUES(" + values + ")";

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public boolean deleteUser(User user) {
		String values;
		String errStr;
		boolean result = true;
		int updateCount;

		try {
			values = String.valueOf(user.getUserID());
			cmdString = "DELETE FROM Users WHERE Id=" + values;

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public boolean updateUser(User currentUser, User newUser) {
		String errStr;
		boolean result = true;
		int updateCount;

		try {
			cmdString = "UPDATE Users SET";
			cmdString += " Type='" + newUser.getType() + "'";
			cmdString += ", FName='" + newUser.getFirstname() + "'";
			cmdString += ", LName='" + newUser.getLastname() + "'";
			cmdString += ", Birthdate='" + newUser.getBirthdate() + "'";
			cmdString += ", Address='" + newUser.getStreetAddress() + "'";
			cmdString += ", City='" + newUser.getCity() + "'";
			cmdString += ", Province='" + newUser.getProvince() + "'";
			cmdString += ", Postal_Code='" + newUser.getPostalCode() + "'";
			cmdString += ", Country='" + newUser.getCountry() + "'";
			cmdString += ", Phone_Num='" + newUser.getPhoneNumber() + "'";
			cmdString += ", Email='" + newUser.getEmail() + "'";
			cmdString += " WHERE Id=" + String.valueOf(currentUser.getUserID());

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	public String checkWarning(Statement st, int updateCount) {
		String result;

		result = null;
		try {
			SQLWarning warning = st.getWarnings();
			if (warning != null) {
				result = warning.getMessage();
			}
		} catch (Exception e) {
			result = processSQLError(e);
		}
		if (updateCount < 1) {
			result = "Tuple not inserted correctly.";
		}
		return result;
	}

	public String processSQLError(Exception e) {
		String result;
		result = "*** SQL Error: " + e.getMessage();
		// e.printStackTrace();
		return result;
	}

	@Override
	public ArrayList<ItemCategory> getItemCategory(ItemCategory itemCategory) {
		String values;
		String itemName, categoryName;
		ItemCategory ic;
		ArrayList<ItemCategory> result = new ArrayList<ItemCategory>();

		try {
			values = itemCategory.getItemName();
			cmdString = "SELECT i.Name, c.Name FROM Items i";
			cmdString += " INNER JOIN JoinItemsCategories jic ON i.Id = jic.Item_Id";
			cmdString += " INNER JOIN Categories c ON jic.Category_Id = c.Id";
			cmdString += " WHERE i.Name='" + values + "'";

			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(cmdString);

			while (rs.next()) {
				itemName = rs.getString(1);
				categoryName = rs.getString(2);

				ic = new ItemCategory(itemName, categoryName);
				result.add(ic);
			}

			s.close();
		} catch (Exception e) {
			processSQLError(e);
		}

		return result;
	}

	@Override
	public ArrayList<ItemCategory> getCategoryItem(ItemCategory itemCategory) {
		String values;
		String itemName, categoryName;
		ItemCategory ic;
		ArrayList<ItemCategory> result = new ArrayList<ItemCategory>();

		try {
			values = itemCategory.getCategoryName();
			cmdString = "SELECT i.Name, c.Name FROM Items i";
			cmdString += " INNER JOIN JoinItemsCategories jic ON i.Id = jic.Item_Id";
			cmdString += " INNER JOIN Categories c ON jic.Category_Id = c.Id";
			cmdString += " WHERE c.Name='" + values + "'";

			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(cmdString);

			while (rs.next()) {
				itemName = rs.getString(1);
				categoryName = rs.getString(2);

				ic = new ItemCategory(itemName, categoryName);
				result.add(ic);
			}

			s.close();
		} catch (Exception e) {
			processSQLError(e);
		}

		return result;
	}

	@Override
	public boolean insertItemCategory(ItemCategory itemCategory) {
		String errStr;
		int updateCount;
		boolean result = true;

		try {
			cmdString = "INSERT INTO JoinItemsCategories(Item_Id, Category_Id)";
			cmdString += " SELECT i.Id, c.Id";
			cmdString += " FROM Items i, Categories c";
			cmdString += " WHERE i.Name='" + itemCategory.getItemName() + "'";
			cmdString += " AND c.Name='" + itemCategory.getCategoryName() + "'";

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public boolean updateItemCategory(ItemCategory currItemCategory,
			ItemCategory newItemCategory) {
		boolean result = true;

		result &= deleteItemCategory(currItemCategory);
		result &= insertItemCategory(newItemCategory);

		return result;
	}

	@Override
	public boolean deleteItemCategory(ItemCategory itemCategory) {
		String errStr;
		boolean result = true;
		int updateCount;

		try {
			cmdString = "DELETE FROM JoinItemsCategories jic";
			cmdString += " WHERE EXISTS(SELECT i.Id, i.Name, c.Id, c.Name";
			cmdString += " FROM Items i, Categories c";
			cmdString += " WHERE i.Name='" + itemCategory.getItemName() + "'";
			cmdString += " AND jic.Item_ID = i.Id";
			cmdString += " AND c.Name='" + itemCategory.getCategoryName() + "'";
			cmdString += " AND jic.Category_Id = c.Id)";

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public ArrayList<ShoppingCart> getShoppingCarts(ShoppingCart shoppingCart) {
		boolean hasItems = false;
		int currUserId = Integer.MIN_VALUE;
		int userId = Integer.MIN_VALUE;
		String itemName = null;
		int quantity = Integer.MIN_VALUE;
		ShoppingCart retVal = null;

		ArrayList<ShoppingCart> shoppingCarts = new ArrayList<ShoppingCart>();
		try {
			cmdString = "SELECT sc.User_Id, i.Name, sc.Quantity";
			cmdString += " FROM ShoppingCarts sc, Items i";
			if (shoppingCart != null)
				cmdString += " WHERE sc.User_Id = " + shoppingCart.getUserId()
						+ " AND sc.Item_Id = i.Id";
			cmdString += " ORDER BY sc.User_Id";

			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(cmdString);

			while (rs.next()) {
				hasItems = true;
				userId = rs.getInt(1);
				itemName = rs.getString(2);
				quantity = rs.getInt(3);

				if (userId != currUserId) {
					if (retVal != null) {
						shoppingCarts.add(retVal);
					}
					
					currUserId = userId;
					retVal = new ShoppingCart(userId);
				}

				retVal.addItem(itemName, quantity);
			}

			s.close();

			if (hasItems)
				shoppingCarts.add(retVal);
		} catch (Exception e) {
			processSQLError(e);
		}

		return shoppingCarts;
	}

	@Override
	public ShoppingCart getShoppingCart(ShoppingCart shoppingCart) {
		ArrayList<ShoppingCart> shoppingCarts = getShoppingCarts(shoppingCart);

		if (shoppingCarts.isEmpty()) {
			return null;
		} else {
			return shoppingCarts.get(0);
		}
	}

	@Override
	public boolean insertShoppingCart(ShoppingCart shoppingCart) {
		String errStr;
		int[] updateCount;
		boolean result = true;

		try {
			Statement s = connection.createStatement();

			for (Entry<String, Integer> e : shoppingCart.getItems().entrySet()) {
				cmdString = "INSERT INTO ShoppingCarts(User_Id, Item_Id, Quantity)";
				cmdString += " SELECT " + shoppingCart.getUserId() + ", i.Id, "
						+ e.getValue();
				cmdString += " FROM Items i";
				cmdString += " WHERE i.Name='" + e.getKey() + "'";

				s.addBatch(cmdString);
			}

			updateCount = s.executeBatch();
			
			if (updateCount.length == 0)
			{
				result = false;
			}

			for (int i = 0; i < updateCount.length; ++i) {
				errStr = checkWarning(s, updateCount[i]);

				if (errStr != null) {
					System.out.println(errStr);
					result = false;
					errStr = null;
				}
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public boolean deleteShoppingCart(ShoppingCart shoppingCart) {
		String values;
		String errStr;
		boolean result = true;
		int updateCount;

		try {
			values = String.valueOf(shoppingCart.getUserId());
			cmdString = "DELETE FROM ShoppingCarts WHERE User_Id=" + values;

			Statement s = connection.createStatement();
			updateCount = s.executeUpdate(cmdString);
			errStr = checkWarning(s, updateCount);

			if (errStr != null)
			{
				System.out.println(errStr);
				result = false;
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}

	@Override
	public boolean updateShoppingCart(ShoppingCart currShoppingCart,
			ShoppingCart newShoppingCart) {
		String errStr;
		boolean result = true;

		try {
			Statement s = connection.createStatement();

			if (currShoppingCart.getUserId() == newShoppingCart.getUserId()) {
				int[] updateCount;

				for (String key : currShoppingCart.getItems().keySet()) {
					if (newShoppingCart.getItems().keySet().contains(key)) {
						// same user_Id, same item, different quantity
						if (newShoppingCart.getItems().get(key) != currShoppingCart
								.getItems().get(key)) {
							cmdString = "UPDATE ShoppingCarts SET";
							cmdString += " Quantity="
									+ newShoppingCart.getItems().get(key);
							cmdString += " WHERE User_Id="
									+ newShoppingCart.getUserId();
							cmdString += " AND Item_Id = (SELECT i.Id FROM Items i WHERE i.Name='" + key + "')";

							s.addBatch(cmdString);
						}
					} else {
						// new set doesn't include item in previous set, item
						// was deleted from cart
						cmdString = "DELETE FROM ShoppingCarts sc";
						cmdString += " WHERE sc.User_Id="
								+ currShoppingCart.getUserId();
						cmdString += " AND EXISTS(SELECT i.Id, i.Name";
						cmdString += " FROM Items i";
						cmdString += " WHERE sc.Item_Id = i.Id";
						cmdString += " AND i.Name='" + key + "')";

						s.addBatch(cmdString);
					}
				}

				for (String key : newShoppingCart.getItems().keySet()) {
					// new set includes item not in previous set, item was added
					// to cart
					if (!currShoppingCart.getItems().keySet().contains(key)) {
						cmdString = "INSERT INTO ShoppingCarts(User_Id, Item_Id, Quantity)";
						cmdString += " SELECT " + newShoppingCart.getUserId()
								+ ", i.Id, "
								+ newShoppingCart.getItems().get(key);
						cmdString += " FROM Items i";
						cmdString += " WHERE i.Name='" + key + "'";

						s.addBatch(cmdString);
					}
				}

				updateCount = s.executeBatch();
				
				if (updateCount.length == 0)
				{
					result = false;
				}

				for (int i = 0; i < updateCount.length; ++i) {
					errStr = checkWarning(s, updateCount[i]);

					if (errStr != null) {
						System.out.println(errStr);
						result = false;
						errStr = null;
					}
				}
			} else {
				// different user entirely, delete and then add entire shopping
				// cart
				result &= deleteShoppingCart(currShoppingCart);
				result &= insertShoppingCart(newShoppingCart);
			}
		} catch (Exception e) {
			processSQLError(e);
			result = false;
		}

		return result;
	}
}
