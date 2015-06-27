package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Achievement;
import db.DBManager;

public class DBAchievementDao implements AchievementDao {
	private Connection connection = DBManager.getDBManager().getConnection();

	@Override
	public Achievement getAchievement(int achievementId) {
		PreparedStatement st = null;
		ResultSet results = null;
		Achievement achievement = null;
		if (achievementId < 0) {
			System.out.println("ValidationException");
			// throw IllegalArgumentException;
		}
		try {
			st = connection
					.prepareStatement("select achievement_points, description from app.achievements where id = ?");
			st.setInt(1, achievementId);
			results = st.executeQuery();
			results.next();
			achievement = new Achievement(achievementId,
					results.getInt("achievement_points"),
					results.getString("description"));
		} catch (SQLException e) {
			System.out.println("error in getAchievement");
			e.printStackTrace();
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
		return achievement;
	}

	@Override
	public ArrayList<Achievement> getAllAchievements() {
		ArrayList<Achievement> list = new ArrayList<Achievement>();
		Statement st = null;
		ResultSet results = null;
		try {
			st = connection.createStatement();
			results = st
					.executeQuery("select id, achievement_points, description from app.achievements");
			while (results.next()) {
				int id = results.getInt("id");
				int achievement_points = results.getInt("achievement_points");
				String description = results.getString("description");
				if (id < 0 || achievement_points < 0 || description == null
						|| description == "") {
					System.out.println("Ivalid data from db - achievement");
					// throw IllegalArgumentException;
				}

				Achievement achievement = new Achievement(id,
						achievement_points, description);
				list.add(achievement);
			}
		} catch (SQLException e) {
			System.out.println("error in select all achievements");
			e.printStackTrace();
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
	public ArrayList<Achievement> getUserAchievements(int userId) {
		ArrayList<Achievement> list = new ArrayList<Achievement>();
		PreparedStatement pst = null;
		ResultSet results = null;
		Achievement achievement = null;
		if (userId < 0) {
			System.out.println("ValidationException");
			// throw IllegalArgumentException;
		}
		try {
			pst = connection
					.prepareStatement("select achievement_id from app.UserAchievements where user_id = ?");
			pst.setInt(1, userId);
			results = pst.executeQuery();
			while (results.next()) {
				int achievement_id = results.getInt("achievement_id");
				achievement = getAchievement(achievement_id);
				list.add(achievement);
			}
		} catch (SQLException e) {
			System.out.println("error in getUserAchievement");
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

		return list;
	}

	@Override
	public boolean setUserAchievement(int user_id, int achievement_id) {
		PreparedStatement pst = null;
		boolean isUpdate = false;
		if (user_id < 0 || achievement_id < 0) {
			System.out.println("ValidationException");
			// throw IllegalArgumentException;
		}
		try {
			pst = connection
					.prepareStatement("insert into app.userAchievements (user_id, achievement_id) values (?,?)");
			pst.setInt(1, user_id);
			pst.setInt(2, achievement_id);
			int executeResult = pst.executeUpdate();
			if (executeResult > 0) {
				isUpdate = true;
			}
		} catch (SQLException e) {
			System.out.println("error in setUserAchievement");
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException e) {
				System.out.println("Error in closing.");
				e.printStackTrace();
			}
		}
		return isUpdate;
	}

	public ArrayList<Achievement> getRecentAcheivment(int score) {
		ArrayList<Achievement> list = new ArrayList<Achievement>();
		PreparedStatement pst = null;
		ResultSet results = null;
		Achievement achievement = null;
		if (score < 0) {
			System.out.println("ValidationException");
			// throw IllegalArgumentException;
		}
		try {
			pst = connection
					.prepareStatement("select id from app.Achievements where achievement_points <= ?");
			pst.setInt(1, score);
			results = pst.executeQuery();
			while (results.next()) {
				int achievement_id = results.getInt("id");
				achievement = getAchievement(achievement_id);
				list.add(achievement);
			}
		} catch (SQLException e) {
			System.out.println("error in getUserAchievement");
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

		return list;
	}

	public boolean existAcheivment(int acheivmentId) {
		boolean result = false;
		PreparedStatement pst = null;
		ResultSet executeResult = null;

		try {
			pst = connection
					.prepareStatement("select user_id from app.userAchievements where achievement_id = ?");
			pst.setInt(1, acheivmentId);
			executeResult = pst.executeQuery();
			if (executeResult.next()) {
				result = true;
			}
		} catch (SQLException e) {
			System.out.println("error in setUserAchievement");
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (executeResult != null) {
					executeResult.close();
				}
			} catch (SQLException e) {
				System.out.println("Error in closing.");
				e.printStackTrace();
			}
		}

		return result;
	}
}
