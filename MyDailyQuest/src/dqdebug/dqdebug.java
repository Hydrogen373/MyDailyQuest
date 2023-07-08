package dqdebug;

import java.sql.*;

import dqdatabase.Database;
import dqgui.GUIManager;

public class dqdebug {
	public static void main(String[] args) {
		Database db = new Database();
		db.regenerate("2", "20220202");
		db.checkDone("4", true, "20230709");
		
//		db.show();
//		db.regenerate("3", "20220203");
//		db.show();
		
		GUIManager gui = new GUIManager();
		
		gui.resetTasks(db.loadAllInfo());

		
		
		gui.show();
		
		return;

	}

}
