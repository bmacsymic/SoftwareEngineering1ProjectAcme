package acme.objects;

public class Category {
	private String name;

	public Category() {
		setName("");
	}

	public Category(String name) {
		setName(name);
	}

	@Override
	public String toString() {
		return "\nName: " + name + "\n";
	}

	@Override
	public boolean equals(Object otherCategory) {
		boolean isEqual = false;
		if (otherCategory != null
				&& ((Category) otherCategory).getName().equals(name))
			isEqual = true;
		return isEqual;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
