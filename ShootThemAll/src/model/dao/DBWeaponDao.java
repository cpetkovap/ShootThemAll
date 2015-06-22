package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;

import model.Weapon;
import db.DBManager;

public class DBWeaponDao implements WeaponDao {
	private Connection connect = DBManager.getDBManager().getConnection();

	@Override
	public ArrayList<Weapon> getWeapons() {
		ArrayList<Weapon> list = new ArrayList<Weapon>();

		Statement statement;
		try {
			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT ID, DAMAGE, PRICE FROM APP.WEAPONS");
			while(resultSet.next()){
				int id = resultSet.getInt("id");
				int damage = resultSet.getInt("damage");
				int price = resultSet.getInt("price");
				Weapon weapon = new Weapon(id, damage, price);
				list.add(weapon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("1");
		}
		

		return list;
	}

	@Override
	public Weapon getWeapon(int weaponId) {
		PreparedStatement statement;
		Weapon weapon = null;
		try {
			statement = connect.prepareStatement("SELECT DAMAGE, PRICE FROM APP.WEAPONS WHERE ID = ?");
			statement.setInt(1, weaponId);
			ResultSet rs = statement.executeQuery();
			rs.next();
			weapon = new Weapon(weaponId, rs.getInt("damage"), rs.getInt("price"));
			System.out.println(weapon.getPrice());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return weapon;
	}

}
