package cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import model.User;
import model.Weapon;
import controller.SettingsManager;
import controller.UsersComparator;

public class Cache implements ICache {
	private Vector<User> users;
	private static Cache cache;

	private Cache() {
		users = new Vector<User>();
	}

	@Override
	public User getUser(int userId) {
		User result = null;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == userId) {
				result = users.get(i);
			}
		}
		return result;
	}

	@Override
	public void addUser(User user) {
		if(users.size() < 20){
			users.add(user);
		}else{
			User index = getUser(getLastActiveUserId());
			deleteUser(index);
			users.add(user);
		}

	}

	@Override
	public void deleteUser(User user) {
		int index = -1;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == user.getId()) {
				index = i;
			}
		}

		users.remove(index);
	}

	@Override
	public void deleteUser(int userId) {
		int index = -1;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == userId) {
				index = i;
			}
		}

		users.remove(index);

	}

	public static Cache getCache() {
		if (cache == null) {
			cache = new Cache();
		}
		return cache;
	}

	@Override
	public Vector<User> getAllUsers() {
		return users;
	}

	@Override
	public void sort() {
		users.sort(new UsersComparator());

	}

	@Override
	public void init() {
		// тук си го пълним с потребители от базата данни 
		//слагаме 20те с най- скорошна активност

		// тест
		User u1 = new User(1, "Ivan", SettingsManager.cryptMD5("Ivan"), "Ivan", 20, 1,
				new Weapon(1, 1, 0), false);
		User u2 = new User(2, "Petko", SettingsManager.cryptMD5("Petko"), "Petko", 10, 0, new Weapon(1,
				1, 0), false);
		User u3 = new User(3, "Tanq", SettingsManager.cryptMD5("Tanq"), "Tanq", 14, 0,
				new Weapon(1, 1, 0), false);

		addUser(u1);
		addUser(u2);
		addUser(u3);
		sort();

	}

	@Override
	public void update(String type, String value, int userId) {
		User user = getUser(userId);

		if (type.equals("password")) {
			user.setPassword(value);
		}

		if (type.equals("email")) {
			user.setEmail(value);
		}

		if (type.equals("allowNotification")) {
			boolean val = Boolean.parseBoolean(value);
			user.setAllowNotification(val);
		}

	}

	@Override
	public boolean existUser(String username, String password) {
		boolean result = false;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)
					&& users.get(i).getPassword().equals(password)) {
				result = true;
			}
		}

		return result;

	}

	@Override
	public int getUserId(String username) {
		int result = -1;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				result = users.get(i).getId();
			}
		}
		return result;
	}

	@Override
	public boolean existUser(String username) {
		boolean result = false;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				result = true;
			}
		}

		return result;
	}

	@Override
	public boolean existUser(int userId) {
		boolean result = false;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == userId) {
				result = true;
			}
		}

		return result;
	}

	@Override
	public boolean isEmpty() {		
		return users.isEmpty();
	}
	
	public int getLastActiveUserId(){
		int result = -1;
		Date date = new Date();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getDate().before(date)) {
				date = users.get(i).getDate();
				result = users.get(i).getId();
			}
		}
		return result;
	}
}