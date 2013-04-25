package acme.business;

import java.util.List;

import acme.application.Main;
import acme.application.Services;
import acme.objects.User;
import acme.persistence.DataAccess;

public class AccessUsers {
	private DataAccess dataAccess;
	private List<User> users;
	private User user;
	private int currentUser;

	public AccessUsers() {
		dataAccess = Services.getDataAccess(Main.dbName);
		users = null;
		user = null;
		currentUser = 0;
	}

	public User getSequential() {
		if (users == null) {
			users = dataAccess.getUsers(null);
			currentUser = 0;
		}
		if (users != null && currentUser < users.size()) {
			user = users.get(currentUser);
			currentUser++;
		} else {
			users = null;
			users = null;
			currentUser = 0;
		}
		return user;
	}

	public int getUsersSize() {
		if (users != null)
			return users.size();
		else
			return 0;
	}

	public boolean insertUser(User currentUser) {
		return dataAccess.insertUser(currentUser);
	}

	public User getUser(User user) {
		return dataAccess.getUser(user);
	}

	public boolean deleteUser(User currentUser) {
		return dataAccess.deleteUser(currentUser);
	}

	public boolean updateUser(User currentUser, User newUser) {
		return dataAccess.updateUser(currentUser, newUser);
	}

}
