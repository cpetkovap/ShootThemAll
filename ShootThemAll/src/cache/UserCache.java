package cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import model.User;
import model.Weapon;
import model.dao.DBUserDao;
import model.dao.DBWeaponDao;
import model.dao.UserDao;
import model.dao.WeaponDao;
import controller.UsersComparator;

public class UserCache implements ICache, IUserCache {
	private Vector<User> users;
	private static UserCache userCache;
	private UserDao ud = new DBUserDao();

	private UserCache() {
		users = new Vector<User>();
	}

	public static UserCache getUserCache() {
		if (userCache == null) {
			userCache = new UserCache();
		}
		return userCache;
	}

	@Override
	public synchronized User getUser(int userId) {
		
		User result = null;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == userId) {
				result = users.get(i);
			}
		}
		if (result == null) {
			result = ud.getUser(userId);
			if (result != null) {
				addUser(result);
			}
		}

		return result;
	}

	@Override
	public synchronized void addUser(User user) {
		if (users.size() < 20) {
			users.add(user);
		} else {
			User index = getUser(getLastActiveUserId());
			deleteUser(index);
			users.add(user);
		}

	}

	@Override
	public synchronized void deleteUser(User user) {
		int index = -1;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == user.getId()) {
				index = i;
			}
		}

		users.remove(index);
	}

	@Override
	public synchronized void deleteUser(int userId) {
		int index = -1;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == userId) {
				index = i;
			}
		}

		users.remove(index);

	}

	@Override
	public synchronized Vector<User> getAllUsers() {
		return users;
	}

	@Override
	public synchronized void sort() {
		users.sort(new UsersComparator());

	}

	@Override
	public void init() {
		// тук си го пълним с потребители от базата данни
		// слагаме 20те с най- скорошна активност

		// тест
		ArrayList<User> users = ud.getTopUsers();
		
//		User u1 = new User(121, "Ivan", SettingsManager.cryptMD5("Ivan"), "Ivan",
//				20, 1, new Weapon(1, 1, 0), false);
//		User u2 = new User(122, "Petko", SettingsManager.cryptMD5("Petko"),
//				"Petko", 10, 0, new Weapon(1, 1, 0), false);
//		User u3 = new User(123, "Tanq", SettingsManager.cryptMD5("Tanq"), "Tanq",
//				14, 0, new Weapon(1, 1, 0), false);
		
		for(int i = 0 ; i < users.size(); i++){
			addUser(users.get(i));
		}

//		addUser(u1);
//		addUser(u2);
//		addUser(u3);
		sort();

	}

	@Override
	public synchronized void update(String type, String value, int userId) {
		User user = getUser(userId);

		if (user != null) {
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

	}
	
	public synchronized void updateLevelUp(int userId){
		User user = getUser(userId);
		user.setLevel(user.getLevel() + 1);
		
	}

	@Override
	public synchronized boolean existUser(String username, String password) {
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
	public synchronized int getUserId(String username) {
		int result = -1;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				result = users.get(i).getId();
			}
		}
		return result;
	}

	@Override
	public synchronized boolean existUser(String username) {
		boolean result = false;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				result = true;
			}
		}

		return result;
	}

	@Override
	public synchronized boolean existUser(int userId) {
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

	public synchronized int getLastActiveUserId() {
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

	public synchronized void printNames() {
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getUsername());
		}
	}

	public void updateScore(int score, int userId) {
		User u = getUser(userId);
		u.setScore(u.getScore() + score);
		
	}

	public void updateUserWeapon(int weaponType, int userId) {
		User u = getUser(userId);
		WeaponDao wd = new DBWeaponDao();
		Weapon weapon = wd.getWeapon(weaponType);
		u.setWeapon(weapon);
		
	}
}