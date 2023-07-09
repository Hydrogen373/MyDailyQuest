package dqdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import dqgui.TaskBox;

import java.sql.*;

public class Database {
	private Connection conn = null;
	private static final String DB_ADDR = "db/main_database.db";
	private PreparedStatement stmt = null;

	private static final String DEFAULT_DATE = "00000000";

	private static ArrayList<String> priorities;

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

	public void test() {
		ensureConnection();
		if (conn != null) {
			try {
				final String sql = "UPDATE PinInfo SET priority = ? WHERE name is ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, 1);
				stmt.setString(2, "newpin");
				stmt.executeUpdate();

				conn.commit();
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

	public boolean setPriority(String name, int priority) {
		boolean result = false;
		ensureConnection();
		if (conn != null) {
			try {
				final String sql = "UPDATE PinInfo SET priority = ? WHERE name = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, priority);
				stmt.setString(2, name);
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

	public boolean checkDone(String uid, boolean done, String tmp_completion_date) {
		boolean result = false;
		ensureConnection();
		if (conn != null) {
			try {
				final String sql = "UPDATE Info SET done = ?, tmp_completion_date = ? WHERE uid is ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, done ? 1 : 0);
				stmt.setString(2, tmp_completion_date);
				stmt.setString(3, uid);

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

	public boolean addTask(String uid, String content) {
		boolean result = false;
		ensureConnection();
		if (conn != null) {
			try {
				String sql = String.format("INSERT INTO Info VALUES (?, ?, null, 0, null)", DEFAULT_DATE, DEFAULT_DATE);
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, uid);
				stmt.setString(2, content);
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

	public boolean removeTask(String uid) {
		boolean result = false;
		ensureConnection();
		if (conn != null) {
			try {
				// TODO Delete from RegenRult
//				 final String sql = "DELETE FROM RegenRule WHERE uid is ?";

				String sql = "DELETE FROM Info WHERE uid is ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, uid);
				stmt.executeUpdate();
				stmt.close();

				sql = "DELETE FROM Pin WHERE uid is ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, uid);
				stmt.executeUpdate();
				stmt.close();

				sql = "DELETE FROM RegenRule WHERE uid is ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, uid);
				stmt.executeUpdate();
				stmt.close();

				conn.commit();

				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		closeAll();
		return result;
	}

	public boolean addPin(String name) {
		boolean result = false;
		ensureConnection();
		if (conn != null) {
			try {
				String sql = "SELECT COUNT(*) FROM PinInfo";
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				int priority = rs.getInt(1) + 1;

				sql = "INSERT INTO PinInfo (name, priority) VALUES (?, ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, name);
				stmt.setInt(2, priority);
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

	public boolean removePin(String name) {
		boolean result = false;
		ensureConnection();
		if (conn != null) {
			try {
				String sql = "DELETE FROM PinInfo WHERE name is ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, name);
				stmt.executeUpdate();
				stmt.close();

				sql = "DELETE FROM Pin WHERE name is ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, name);
				stmt.executeUpdate();
				stmt.close();

				conn.commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		closeAll();
		return result;
	}

	public boolean setPin(String uid, String pinName) {
		boolean result = false;
		ensureConnection();
		if (conn != null) {
			try {
				String sql = "INSERT INTO Pin (uid, name) VALUES (?, ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, uid);
				stmt.setString(2, pinName);
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

	public ArrayList<TaskBox> sort(ArrayList<TaskBox> tasks) {
		ArrayList<TaskBox> result = null;
		ensureConnection();
		if (conn != null) {
			try {
				priorities = new ArrayList<String>();

				String sql = "SELECT * FROM PinInfo";
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					priorities.add(rs.getString("name"));
				}
				rs.close();
				stmt.close();

				Collections.reverse(priorities);
				for (String name : priorities) {
					sql = "SELECT * FROM Pin WHERE uid is ? AND name is ?";
					stmt = conn.prepareStatement(sql);
					ArrayList<TaskBox> trueList = new ArrayList<TaskBox>(), falsesList = new ArrayList<TaskBox>();
					for (TaskBox task : tasks) {
						stmt.setString(1, task.uid);
						stmt.setString(2, name);
						rs = stmt.executeQuery();
						if (rs.next()) {
							trueList.add(task);
						} else {
							falsesList.add(task);
						}
					}
					trueList.addAll(falsesList);
					tasks = trueList;
				}

				priorities = null;

				ArrayList<TaskBox> trueList = new ArrayList<TaskBox>(),
						falseList = new ArrayList<TaskBox>();
				for(TaskBox task : tasks) {
					if (!task.done) {
						trueList.add(task);
					}else {
						falseList.add(task);
					}
				}
				trueList.addAll(falseList);

				result = trueList;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		

		closeAll();
		return result;
	}

}
