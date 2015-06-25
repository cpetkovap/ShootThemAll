package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Achievement;
import db.DBManager;

public class DBAchievementDao implements AchievementDao{
	private Connection connection = DBManager.getDBManager().getConnection();

	@Override
	public Achievement getAchievement(int achievementId) {
		PreparedStatement statement;
		Achievement achievement = null;
		if( achievementId <0){
			System.out.println("ValidationException");
			//throw IllegalArgumentException;
			}
		try {
			statement = connection.prepareStatement("select achievement_points, description from app.achievements where id = ?");
			statement.setInt(1, achievementId);
			ResultSet rs = statement.executeQuery();
			rs.next();
			achievement = new Achievement(achievementId, rs.getInt("achievement_points"), rs.getString("description"));
		} catch (SQLException e) {
			System.out.println("error in getAchievement");
			e.printStackTrace();
		}
		return achievement;
	}

	@Override
	public ArrayList<Achievement> getAllAchievements() {
		ArrayList<Achievement> list = new ArrayList<Achievement>();
		Statement st;
		try {
			st = connection.createStatement();
			ResultSet results = st
					.executeQuery("select id, achievement_points, description from app.achievements");
			while (results.next()) {
				int id = results.getInt("id");
				int achievement_points = results.getInt("achievement_points");
				String description = results.getString("description");
				if(id<0 || achievement_points<0 || description == null || description == ""){
					System.out.println("Ivalid data from db - achievement");
					//throw IllegalArgumentException;
				}
				
				Achievement achievement = new Achievement(id, achievement_points, description);
				list.add(achievement);
			}
		} catch (SQLException e) {
			System.out.println("error in select all achievements");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList<Achievement> getUserAchievements(int userId) {
		ArrayList<Achievement> list = new ArrayList<Achievement>();
		PreparedStatement statement;
		Achievement achievement = null;
		if( userId <0){
			System.out.println("ValidationException");
			//throw IllegalArgumentException;
			}
		try {
			statement = connection.prepareStatement("select achievement_id from app.UserAchievements where user_id = ?");
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
			 int achievement_id = rs.getInt("achievement_id");
			 achievement = getAchievement(achievement_id);
		list.add(achievement);
			}
		} catch (SQLException e) {
			System.out.println("error in getUserAchievement");
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public boolean setUserAchievement(int user_id, int achievement_id) {
		PreparedStatement statement;
		boolean isUpdate = false;
		if( user_id <0 || achievement_id<0){
			System.out.println("ValidationException");
			//throw IllegalArgumentException;
			}
		try {
			statement = connection.prepareStatement("insert into app.userAchievements (user_id, achievement_id) values (?,?)");
			statement.setInt(1, user_id);
			statement.setInt(2, achievement_id);
			int executeResult = statement.executeUpdate();
			if(executeResult>0){
				isUpdate = true;
			}
		} catch (SQLException e) {
			System.out.println("error in setUserAchievement");
			e.printStackTrace();
		}
		return isUpdate;	
	}
}