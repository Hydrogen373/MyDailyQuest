package dqdebug;

import java.sql.*;
import java.util.ArrayList;

import dqdatabase.Database;
import dqgui.GUIManager;
import dqgui.TaskBox;

public class dqdebug {
	public static void main(String[] args) {
		Database db = new Database();
		db.regenerate("2", "20220202");
		db.checkDone("4", true, "20230709");
		db.addTask("debug", "debugtest05");
		
		db.removePin("newpin");
		db.removePin("lowerPin");
		db.addPin("lowerPin");
		db.addPin("newpin");
		
		db.setPin("debug", "newpin");
		db.setPin("2", "newpin");
		
		
		
//		db.show();
//		db.regenerate("3", "20220203");
//		db.show();
		
		GUIManager gui = new GUIManager();
		
		ArrayList<TaskBox> tasks = db.loadAllInfo();
		tasks = db.sort(tasks);

		gui.resetTasks(tasks);

		
		
		gui.show();
		
		db.removeTask("debug");
		
		return;

	}

}
