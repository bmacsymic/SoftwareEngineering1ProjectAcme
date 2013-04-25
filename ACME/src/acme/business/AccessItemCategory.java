package acme.business;

import java.util.ArrayList;

import acme.application.Main;
import acme.application.Services;
import acme.objects.ItemCategory;
import acme.persistence.DataAccess;

public class AccessItemCategory {
	private DataAccess dataAccess;

	public AccessItemCategory() {
		dataAccess = Services.getDataAccess(Main.dbName);
	}

	public ArrayList<ItemCategory> getItemCategory(ItemCategory itemCategory) {
		if (itemCategory != null){
			ArrayList<ItemCategory> resultSet = dataAccess.getItemCategory(itemCategory);
			
			if (resultSet.size() != 0)
				return resultSet;
		}

		return null;
	}

	public ArrayList<ItemCategory> getCategoryItem(ItemCategory itemCategory) {
		if (itemCategory != null){
			ArrayList<ItemCategory> resultSet = dataAccess.getCategoryItem(itemCategory);
			
			if (resultSet.size() != 0)
				return resultSet;
		}

		return null;
	}

	public boolean insertItemCategory(ItemCategory itemCategory) {
		if (itemCategory != null && itemCategory.getItemName() != null && itemCategory.getCategoryName() != null)
			return dataAccess.insertItemCategory(itemCategory);
		else
			return false;
	}

	public boolean updateItemCategory(ItemCategory currItemCategory,
			ItemCategory newItemCategory) {
		if (currItemCategory != null && newItemCategory != null && currItemCategory.getItemName() != null
				&& currItemCategory.getCategoryName() != null && newItemCategory.getItemName() != null
				&& newItemCategory.getCategoryName() != null)
			return dataAccess.updateItemCategory(currItemCategory, newItemCategory);
		else
			return false;
	}

	public boolean deleteItemCategory(ItemCategory itemCategory) {
		if (itemCategory != null && itemCategory.getItemName() != null && itemCategory.getCategoryName() != null)
			return dataAccess.deleteItemCategory(itemCategory);
		else
			return false;
	}
}
