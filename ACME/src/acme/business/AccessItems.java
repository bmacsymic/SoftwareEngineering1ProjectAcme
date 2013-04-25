package acme.business;

import java.util.List;

import acme.application.Main;
import acme.application.Services;
import acme.objects.Item;
import acme.persistence.DataAccess;

public class AccessItems {
	private DataAccess dataAccess;
	private List<Item> items;
	private Item item;
	private int currentItem;

	public AccessItems() {
		dataAccess = Services.getDataAccess(Main.dbName);
		items = null;
		item = null;
		currentItem = 0;
	}

	public int getItemsSize() {
		return dataAccess.getItems(null).size();
	}

	public void printItemsList() {
		items = dataAccess.getItems(null);
		for (int i = 0; i < items.size(); i++) {
			System.out.print(items.get(i));
		}
	}

	public Item getSequential() {
		if (items == null) {
			items = dataAccess.getItems(null);
			currentItem = 0;
		}
		if (currentItem < items.size()) {
			item = items.get(currentItem);
			currentItem++;
		} else {
			items = null;
			item = null;
			currentItem = 0;
		}
		return item;
	}

	public boolean insertItem(Item currentItem) {
		return dataAccess.insertItem(currentItem);
	}

	public Item getItem(Item currentItem) {
		if (currentItem != null)
			return dataAccess.getItem(currentItem);
		else
			return null;
	}

	public boolean deleteItem(Item currentItem) {
		return dataAccess.deleteItem(currentItem);
	}

	public boolean updateItem(Item currentItem, Item newItem) {
		return dataAccess.updateItem(currentItem, newItem);
	}
}
