package dqdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import dqgui.TaskBox;

import java.sql.*;

public class Database {
	private Connection conn = null;
	private static final String DB_ADDR = "db/main_database.db";
	private PreparedStatement stmt = null;

	private void closeAll() {
		if (this.stmt != null) {
			try {
				this.stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				this.stmt = null;
			}
		}
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

	private Connection createConnection() {
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

	private Connection ensureConnection() {
		try {
			if (this.conn == null || this.conn.isValid(500)) {
				closeAll();
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
				final String sql = "SELECT uid, content FROM Info";
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
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
		closeAll();
	}

	public boolean regenerate(String uid, String recent_completion_date) {
		boolean result = false;
		createConnection();
		if (conn != null) {
			try {
//				final String command = "UPDATE Info SET done = 0 WHERE uid is " + uid;
//				final String command2 = "UPDATE Info SET recent_completion_date = '" + recent_completion_date
//						+ "' WHERE uid is " + uid;
//				stat.executeUpdate(command);
//				stat.executeUpdate(command2);
				
				final String sql = "UPDATE Info SET done = ?, recent_completion_date = ? WHERE uid is ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, 0);
				stmt.setString(2, recent_completion_date);
				stmt.setString(2, uid);
				stmt.executeUpdate();

				conn.commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		closeAll();
		return result;
	}

	public ArrayList<TaskBox> loadAllInfo() {
		ensureConnection();
		ArrayList<TaskBox> result = new ArrayList<TaskBox>();
		if (conn != null) {
			try {
				final String sql = "SELECT * FROM Info";
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
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
		closeAll();
		return result;
	}
	
	public boolean checkDone(String uid, boolean done, String tmp_completion_date) {
		boolean result = false;
		ensureConnection();
		if(conn != null) {
			try {
				final String sql = "UPDATE Info SET done = ?, tmp_completion_date = ? WHERE uid is ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, done?1:0);
				stmt.setString(2, tmp_completion_date);
				stmt.setString(3, uid);

				stmt.executeUpdate();

				conn.commit();
				result = true;
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		closeAll();
		return result;
	}

}
