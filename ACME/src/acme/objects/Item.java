package acme.objects;

public class Item {
	private String name;
	private String description;
	private double price;
	private int quantity;
	private String imageFileName;

	public Item() {
		setName("");
		setDescription("");
		setPrice(0.00);
		setQuantity(0);
		setFileName("");
	}

	public Item(double price, int quantity) {
		setName("default1");
		setDescription("");
		setPrice(price);
		setQuantity(quantity);
		setFileName("");
	}

	public Item(String name) {
		setName(name);
		setDescription("");
		setPrice(0.00);
		setQuantity(0);
		setFileName("");
	}

	public Item(String name, String description, double price, int quantity,
			String imageFile) {
		setName(name);
		setDescription(description);
		setPrice(price);
		setQuantity(quantity);
		setFileName(imageFile);
	}

	@Override
	public boolean equals(Object otherItem) {
		boolean isEqual = false;
		if (otherItem != null && ((Item) otherItem).getName().equals(name))
			isEqual = true;
		return isEqual;
	}

	@Override
	public String toString() {
		return "\nName: " + name + "\nDescription: " + description
				+ "\nPrice: " + price + "\nQuantity: " + quantity;
	}

	public boolean contains(String string) {
		boolean isContained = false;
		if (string != null && !string.equals("")) {
			if (getName().indexOf(string) != -1
					|| getDescription().indexOf(string) != -1)
				isContained = true;
		}
		return isContained;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getFileName() {
		return imageFileName;
	}
}
