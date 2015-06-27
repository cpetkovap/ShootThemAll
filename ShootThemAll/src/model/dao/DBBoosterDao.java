package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBManager;
import model.Booster;

public class DBBoosterDao implements BoosterDao{

	private Connection connect = DBManager.getDBManager().getConnection();
	
	@Override
	public Booster getBooster(int boosterId) {
		Booster result = new Booster(3, 1000, "Add points.");;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if(boosterId<0){
			System.out.println("ValidationException ");
			//throw IllegalArgumentException;
		}
		try {
			pst = connect.prepareStatement("select duration, description from app.boosters where id = ?");
			pst.setInt(1, boosterId);
			rs = pst.executeQuery();
			rs.next();
			long duration = rs.getLong("duration");
			String description = rs.getString("description");
			result = new Booster(boosterId, duration, description);			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (pst != null) {
					pst.close();
				}
				if(rs != null){
				rs.close();
			}
				} catch (SQLException e) {
			System.out.println("Error in closing.");
					e.printStackTrace();
				}
		}
		
	
		
		return result;
	}

}
