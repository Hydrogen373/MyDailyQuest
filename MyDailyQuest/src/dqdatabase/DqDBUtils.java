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

		String sql = "UPDATE Task SET recentCompletionDate = tmpCompletionDate, done = 0 " + "WHERE uid in "
				+ "(SELECT uid FROM RegenRule WHERE rule = ? OR rule = ?) " + "and uid in "
				+ "(SELECT uid FROM Info WHERE done = 1 AND tmp_completion_date != ?)";

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
			final String sql = "SELECT * FROM Info";
			PreparedStatement prst = db.prepareStatement(sql);
			ResultSet rs = prst.executeQuery();
			while (rs.next()) {
				String uid = rs.getString("uid");
				String content = rs.getString("content");
				boolean done = (rs.getInt("done") == 1);
				String date = rs.getString((done ? "tmp_completion_date" : "recent_completion_date"));

				result.put(uid, new TaskBox(uid, content, date, done));
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
			PreparedStatement prst = db.prepareStatement("SELECT uid FROM Info WHERE uid = ?");
			while (true) {
				result = UUID.randomUUID().toString();

				prst.setString(1, result);
				ResultSet rs = prst.executeQuery();
				if (!rs.next()) {
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

	static public boolean addTask(String taskId, String content) {
		boolean result = false;

		DqDBConnection db = new DqDBConnection();
		String sql = "INSERT INTO Tasks VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement prst = db.prepareStatement(sql);
			prst.setString(1, taskId);
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

	static public boolean removeTask(String taskId) {
		boolean result = false;
		DqDBConnection db = new DqDBConnection();
		String sql = "DELETE FROM Tasks WHERE taskId == ?";
		try {
			PreparedStatement prst = db.prepareStatement(sql);
			prst.setString(1, taskId);
			prst.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

		db.closeAll();
		return result;
	}
	
	static private class TaskSorter{
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
