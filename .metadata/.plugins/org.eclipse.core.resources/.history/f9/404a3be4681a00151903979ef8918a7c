package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.User;
import model.Weapon;

public interface UserDao {
	
	String DATASOURCE_DB = "DATASOURCE_DB";
	
	boolean hasQuery();
	List getAllUsers();
	ArrayList<Integer> getUnlockedWeapons(int userId);
	User getUser(int userId);
	User getUser(String username);
	int addUser(User u);
	void deletUser(int userId);
	void updatePassword(String password, int userId);
	void updateEmail(String email, int userId);
	void updateScore(int score, int userId);
	void updateLevel(int level, int userId);
	void updateLevelUp(int userId);
	void updateNotification(boolean noficationAllow, int userId);
	
	void updateUser(String type, String value, int userId);
	int existUser(String username, String password);
	Weapon getUserWeapon(int userId);
	int getUserScore(int userId);
	int getUserLevel(int userId);
	
	static UserDao getUserDao(String type){
		if(type.equals(DATASOURCE_DB)){
			return new DBUserDao();
		}else{
			//throw IllegalArgumentException;
			return null;
		}
		
	}
	

}
