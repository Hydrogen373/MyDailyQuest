package dqdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DqDBConnection {
	private Connection conn = null;
	private static final String DB_ADDR = "db/main_database.db";
	private ArrayList<PreparedStatement> prsts = new ArrayList<PreparedStatement>();
	
	DqDBConnection(){
		ensureConnection();
	}

	public void closeAll() {
		for (PreparedStatement prst : prsts) {
			if(prst != null) {
				try {
					prst.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			prsts.remove(prst);
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.conn;
	}

	public Connection ensureConnection() {
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
	
	public PreparedStatement prepareStatement(String sql) {
		try {
			return conn.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
