package dqdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import dqgui.TaskBox;

import java.sql.*;

public class Database {
	private Connection conn = null;
	private static final String DB_ADDR = "db/main_database.db";

	public void closeConnection() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				this.conn = null;
			}
		}
	}

	public Connection createConnection() {
		if (this.conn == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				this.conn = DriverManager.getConnection("jdbc:sqlite:" + DB_ADDR);
				conn.setAutoCommit(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.conn;
	}

	public Connection ensureConnection() {
		try {
			if (this.conn == null || this.conn.isValid(500)) {
				closeConnection();
				createConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.conn;
	}

	public void show() {
		ensureConnection();
		if (conn != null) {
			try {
				final String command_select = "SELECT uid, content FROM Info";
				Statement stat = conn.createStatement();
				ResultSet rs = stat.executeQuery(command_select);
				while (rs.next()) {
					String id = rs.getString("uid");
					String name = rs.getString("content");
					System.out.println(id + "\t" + name);
				}

//				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		closeConnection();
	}

	public ArrayList<Task> getList() {
		ArrayList<Task> result = new ArrayList<Task>();
		createConnection();

		// TODO GetList

		closeConnection();
		return result;
	}

	public boolean regenerate(String uid, String recent_completion_date) {
		boolean result = false;
		createConnection();
		if (conn != null) {
			try {
				final String command = "UPDATE Info SET done = 0 WHERE uid is " + uid;
				final String command2 = "UPDATE Info SET recent_completion_date = '" + recent_completion_date + "' WHERE uid is " + uid;
				Statement stat = conn.createStatement();
				stat.executeUpdate(command);
				stat.executeUpdate(command2);
				conn.commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		closeConnection();
		return result;
	}
	
	public ArrayList<TaskBox> loadAllInfo() {
		ensureConnection();
		ArrayList<TaskBox> result = new ArrayList<TaskBox>();
		if(conn != null) {
			try {
				final String command = "SELECT * FROM Info";
				Statement stat = conn.createStatement();
				ResultSet rs = stat.executeQuery(command);
				while (rs.next()) {
					String uid = rs.getString("uid");
					String content = rs.getString("content");
					String recent_completion_date = rs.getString("recent_completion_date");
					boolean done = (rs.getInt("done") == 1);
					String tmp_completion_date = rs.getString("tmp_completion_date");
					
					result.add(new TaskBox(uid, content, recent_completion_date, done, tmp_completion_date));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		closeConnection();
		return result;
	}

}
