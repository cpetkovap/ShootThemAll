package model.dao;

import java.util.List;

import model.User;
import model.Weapon;
import java.lang.IllegalArgumentException;

public interface UserDao {
	
	String DATASOURCE_DB = "DATASOURCE_DB";
	
	List getAllUsers();
	User getUser(int userId);
	User getUser(String username);
	void addUser(User u);
	void deleteUser(User u);
	void deletUser(int userId);
	void updateUser(String type, int userId);
	boolean existUser(String username, String password);
	Weapon getUserWeapon(int userId);
	int getUserScore(int userId);
	
	static UserDao getUserDao(String type){
		if(type.equals(DATASOURCE_DB)){
			return new DBUserDao();
		}else{
			//throw IllegalArgumentException;
			return null;
		}
		
	}
	

}
