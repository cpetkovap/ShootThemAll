package cache;

import java.util.Vector;

import model.User;

public interface IUserCache {
	
	void init();
	User getUser(int userId);
	int getUserId(String username);
	void addUser(User user);
	void deleteUser(User user);
	void deleteUser(int userId);
	void update(String type, String value, int userId);
	boolean existUser(String username, String password);
	boolean existUser(String username);
	boolean existUser(int userId);
    Vector<User> getAllUsers();
    boolean isEmpty();
    void sort();

	
}
