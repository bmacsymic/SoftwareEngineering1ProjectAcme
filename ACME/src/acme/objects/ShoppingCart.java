package acme.objects;

import java.util.HashMap;

public class ShoppingCart {
	private int userID;
	private HashMap<String, Integer> items;

	public ShoppingCart(int userId) {
		this.userID = userId;
		items = new HashMap<String, Integer>();
	}

	public int getUserId() {
		return userID;
	}

	public void setUserId(int userId) {
		this.userID = userId;
	}

	public HashMap<String, Integer> getItems() {
		return new HashMap<String, Integer>(items);
	}

	public boolean contains(String itemName) {
		return items.containsKey(itemName);
	}

	public void setItems(HashMap<String, Integer> items) {
		this.items = items;
	}

	public boolean addItem(String item, int quantity) {
		boolean wasInserted = false;
		if (item != null) {
			items.put(item, quantity);
			wasInserted = true;
		}
		return wasInserted;
	}

	public boolean removeItem(String item) {
		boolean wasRemoved = false;
		if (item != null) {
			if (items.remove(item) != null)
				wasRemoved = true;
		}
		return wasRemoved;
	}

	public int getSize() {
		return items.size();
	}

	public void emptyCart() {
		items.clear();
	}

	@Override
	public boolean equals(Object otherCart) {
		boolean isEqual = false;
		if (otherCart != null
				&& ((ShoppingCart) otherCart).getUserId() == userID)
			isEqual = true;
		return isEqual;
	}

}
