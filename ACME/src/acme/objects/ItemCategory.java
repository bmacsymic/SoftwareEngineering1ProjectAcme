package acme.objects;

public class ItemCategory {
	private String itemName;
	private String categoryName;

	public String getItemName() {
		return itemName;
	}

	public String getCategoryName() {
		return categoryName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ItemCategory() {
	}

	public ItemCategory(String itemName, String categoryName) {
		this.itemName = itemName;
		this.categoryName = categoryName;
	}

	@Override
	public boolean equals(Object object) {
		boolean result;
		ItemCategory ic;

		result = false;

		if (object instanceof ItemCategory) {
			ic = (ItemCategory) object;
			if ((((ic.itemName == null) && (itemName == null)) || ((ic.itemName != null) && (ic.itemName
					.equals(itemName))))
					&& (((ic.categoryName == null) && (categoryName == null)) || ((ic.categoryName != null) && (ic.categoryName
							.equals(categoryName))))) {
				result = true;
			}
		}
		return result;
	}
}
