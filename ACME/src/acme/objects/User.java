package acme.objects;

public class User {
	private static int userIDCount = 0;
	private int userID;
	private String firstname;
	private String lastname;
	private String birthdate;
	private String streetAddress;
	private String city;
	private String province;
	private String postalCode;
	private String country;
	private String phoneNumber;
	private String email;
	private String type;

	public User() {
		setDefaultValues();
		setUserID(userIDCount);
		userIDCount++;
	}

	public User(int userID) {
		setDefaultValues();
		setUserID(userID);
	}

	public User(String firstname, String lastname) {
		setDefaultValues();
		this.firstname = firstname;
		this.lastname = lastname;
		setUserID(userIDCount);
		userIDCount++;
	}

	public User(String firstname, String lastname, String birthdate,
			String streetAddress, String city, String province,
			String postalCode, String country, String phoneNumber,
			String email, String type) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.streetAddress = streetAddress;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.type = type;
		setUserID(userIDCount);
		userIDCount++;
	}

	public User(String type) {
		setDefaultValues();
		setType(type);
		setUserID(userIDCount);
		userIDCount++;
	}

	public void updateUser(String firstname, String lastname, String birthdate,
			String streetAddress, String city, String province,
			String postalCode, String country, String phoneNumber,
			String email, String type) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.streetAddress = streetAddress;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.type = type;
	}

	public void setDefaultValues() {
		this.firstname = "";
		this.lastname = "";
		this.birthdate = "";
		this.streetAddress = "";
		this.city = "";
		this.province = "";
		this.postalCode = "";
		this.country = "";
		this.phoneNumber = "";
		this.email = "";
		this.type = "";
	}

	@Override
	public boolean equals(Object user) {
		boolean isEqual = false;
		if (user != null && ((User) user).getUserID() == this.getUserID())
			isEqual = true;
		return isEqual;
	}

	// accessors
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return province;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getUserID() {
		return userID;
	}
}
