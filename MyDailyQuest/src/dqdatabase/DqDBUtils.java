package dqdatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import dqgui.TaskBox;

public class DqDBUtils {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final String DEFAULT_DATE = "00000000";
	private static ArrayList<String> tags = null;

	@SuppressWarnings("deprecation")
	static public ArrayList<String> regen() {
		ArrayList<String> result = new ArrayList<String>();
		Date today = Calendar.getInstance().getTime();

		String sql = "UPDATE tasks SET recent_completion_date = tmp_completion_date, done = 0 " 
				+ "WHERE taskID in ("
				+ "SELECT taskID FROM regeneration_rules WHERE rule = ? OR rule = ? " + "INTERSECT "
				+ "SELECT taskID FROM tasks WHERE done = 1 AND tmp_completion_date <> ?"
				+ ")";

		DqDBConnection db = new DqDBConnection();
		try {
			PreparedStatement prst = db.prepareStatement(sql);
			prst.setInt(1, today.getDate());
			prst.setInt(2, today.getDay() + 32);
			prst.setString(3, sdf.format(today));
			prst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		db.closeAll();
		return result;
	}

	static public HashMap<String, TaskBox> loadAllTask() {
		HashMap<String, TaskBox> result = new HashMap<>();
		DqDBConnection db = new DqDBConnection();
		try {
			final String sql = "SELECT * FROM tasks";
			PreparedStatement prst = db.prepareStatement(sql);
			ResultSet rs = prst.executeQuery();
			while (rs.next()) {
				String taskID = rs.getString("taskID");
				String content = rs.getString("content");
				boolean done = (rs.getInt("done") == 1);
				String date = rs.getString((done ? "tmp_completion_date" : "recent_completion_date"));

				result.put(taskID, new TaskBox(taskID, content, date, done));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.closeAll();
		return result;
	}

	static public String generateUID() {
		String result = null;
		DqDBConnection db = new DqDBConnection();
		db.ensureConnection();
		try {
			PreparedStatement prst = db.prepareStatement("SELECT count(taskID) FROM tasks WHERE taskID = ?");
			while (true) {
				result = UUID.randomUUID().toString();

				prst.setString(1, result);
				ResultSet rs = prst.executeQuery();
				rs.next();
				if (rs.getInt(1) == 0) {
					break;
				} else {
					// regenerate UID
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("DqDatabse fail to verify exclusiveness of uuid: err");
			return UUID.randomUUID().toString();
		}
		db.closeAll();
		return result;
	}

	static public boolean addTask(String taskID, String content) {
		boolean result = false;

		DqDBConnection db = new DqDBConnection();
		String sql = "INSERT INTO tasks VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement prst = db.prepareStatement(sql);
			prst.setString(1, taskID);
			prst.setString(2, content);
			prst.setString(3, DEFAULT_DATE);
			prst.setInt(4, 0);
			prst.setString(5, DEFAULT_DATE);
			prst.execute();

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.closeAll();
		return result;

	}

	static public boolean removeTask(String taskID) {
		boolean result = false;
		DqDBConnection db = new DqDBConnection();
		String sql = "DELETE FROM tasks WHERE taskID = ?";
		try {
			PreparedStatement prst = db.prepareStatement(sql);
			prst.setString(1, taskID);
			prst.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

		db.closeAll();
		return result;
	}
	
	static private class TaskSorter{
		// TODO
		static private String sql = "SELECT FROM Tags WHERE taskId == ? AND tag == ?";

		DqDBConnection db = null;
		PreparedStatement prst = null;
		ArrayList<String> tagsPriority = null;

		public TaskSorter(){
			db = new DqDBConnection();
			prst = db.prepareStatement(sql);
			
			String sqlGetPriorities = "SELECT * FROM PinInfo ORDER BY priority";
			PreparedStatement priorityPrst = db.prepareStatement(sqlGetPriorities);
			try {
				ResultSet rs = priorityPrst.executeQuery();
				while (rs.next()) {
					tagsPriority.add(rs.getString(2));
				}
				priorityPrst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private int isPreferred(String taskIdTarget, String taskIdStandard) {
			int result = 0;
			try {
				for (String tag : tagsPriority) {
					prst.setString(2, tag);
					prst.setString(1, taskIdTarget);
					prst.execute();
					// TODO

					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			this.end();
			return result;
		}
		
		
		
		public void end() {
			db.closeAll();
			db = null;
			prst = null;
		}
		
	}
}
