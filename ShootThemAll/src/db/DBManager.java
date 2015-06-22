package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	private static DBManager dbinstances;

	private static final String URL = "jdbc:derby:/Users/jgeorgiev/MyDB;create=true";
	private static final String USERNAME = "user";
	private static final String PASSWORD = "123";

	Connection c;

	private DBManager() {

		connectToDB();
	}

	private void connectToDB() {
		// connect

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			c = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}

	}

	public Connection getConnection() {
		if (c == null) {
			try {
				Class.forName("org.apache.derby.jdbc.ClientDriver");
				c = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (SQLException ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		return c;
	}
	
	public void destroyConnection(){
		try {
			c.close();
		} catch (SQLException e) {
			
			System.out.println("error in destroy connection");
			e.printStackTrace();
		}
	}

	public static DBManager getDBManager() {
		if (dbinstances == null) {
			return new DBManager();
		}
		return dbinstances;

	}

}
