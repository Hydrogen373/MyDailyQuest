package dqdebug;

import java.sql.*;

public class dqdebug {
	private static Connection conn = null;
	private static final String DB_ADDR = "db/main_database.db";

	public void main(String[] args) {
		// connect
		conn = createConnection();

		// load data
		if (conn != null) {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("SELECT uid, content FROM Info");
			while (rs.next()) {
				String id = rs.getString("uid");
				String name = rs.getString("content");
				System.out.println(id + "\t" + name);
			}
		}

		// disconnect
		closeConnection();

	}

	public void closeConnection() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Connection createConnection() {
		if (this.conn == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				this.conn = DriverManager.getConnection("jdbc:sqlite:" + DB_ADDR);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.conn;
	}

}
