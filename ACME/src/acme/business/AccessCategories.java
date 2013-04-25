package acme.business;

import java.util.List;

import acme.application.Main;
import acme.application.Services;
import acme.objects.Category;
import acme.persistence.DataAccess;

public class AccessCategories {
	private DataAccess dataAccess;
	private List<Category> categories;
	private Category category;
	private int currentCategory;

	public AccessCategories() {
		dataAccess = Services.getDataAccess(Main.dbName);
		categories = null;
		category = null;
		currentCategory = 0;
	}

	public Category getSequential() {
		if (categories == null) {
			categories = dataAccess.getCategories(null);
			currentCategory = 0;
		}
		if (currentCategory < categories.size()) {
			category = categories.get(currentCategory);
			currentCategory++;
		} else {
			categories = null;
			category = null;
			currentCategory = 0;
		}
		return category;
	}

	public boolean insertCategory(Category currentCategory) {
		return dataAccess.insertCategory(currentCategory);
	}

	public Category getCategory(Category category) {
		if (category != null)
			return dataAccess.getCategory(category);
		else
			return null;
	}

	public boolean deleteCategory(Category currentCategory) {
		return dataAccess.deleteCategory(currentCategory);
	}

	public boolean updateCategory(Category currentCategory, Category newCategory) {
		return dataAccess.updateCategory(currentCategory, newCategory);
	}
}
