package model.dao;

import java.util.ArrayList;

import model.Weapon;

public interface WeaponDao {
	
	String DATASOURCE_DB = "DATASOURCE_DB";
	
	ArrayList<Weapon> getWeapons();
	Weapon getWeapon(int weaponId);
	
	static WeaponDao getWeaponDao(String type){
		if(type.equals(DATASOURCE_DB)){
			return new DBWeaponDao();
		}else{
			//throw IllegalArgumentException;
			return null;
		}
		
	}

}
