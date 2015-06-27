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

		Statement st = null;
		ResultSet results = null;
		try {
			st = connect.createStatement();
			results = st
					.executeQuery("SELECT ID, DAMAGE, PRICE FROM APP.WEAPONS");
			while (results.next()) {
				int id = results.getInt("id");
				int damage = results.getInt("damage");
				int price = results.getInt("price");
				if (id < 0 || damage < 0 || price < 0) {
					System.out.println("Ivalid data from db");
					// throw IllegalArgumentException;
				}
				Weapon weapon = new Weapon(id, damage, price);
				list.add(weapon);
			}
		} catch (SQLException e) {
			System.out.println("Error in get all weapons.");
			e.printStackTrace();
			System.out.println("1");
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				System.out.println("Error in closing.");
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public Weapon getWeapon(int weaponId) {
		PreparedStatement pst = null;
		ResultSet results = null;
		Weapon weapon = null;
		if (weaponId < 0) {
			System.out.println("ValidationException");
			// throw IllegalArgumentException;
		}
		try {
			pst = connect
					.prepareStatement("select damage, price from app.weapons where id = ?");
			pst.setInt(1, weaponId);
			results = pst.executeQuery();
			results.next();
			weapon = new Weapon(weaponId, results.getInt("damage"),
					results.getInt("price"));
			System.out.println(weapon.getPrice());
		} catch (SQLException e) {
			System.out.println("Error in get weapon.");
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				System.out.println("Error in closing.");
				e.printStackTrace();
			}
		}

		return weapon;
	}

}
