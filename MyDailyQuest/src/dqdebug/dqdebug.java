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
		db.removeTask("debug");
		db.addTask("debug", "debugtest05");
		
		db.removePin("highpin");
		db.removePin("lowerPin");
		db.addPin("highpin");
		db.addPin("lowerPin");
		
		db.setPin("debug", "highpin");
		db.setPin("2", "highpin");
		db.setPin("3", "lowerPin");
		
		
		
//		db.show();
//		db.regenerate("3", "20220203");
//		db.show();
		
		GUIManager gui = new GUIManager();
		
		ArrayList<TaskBox> tasks = db.loadAllInfo();
		tasks = db.sort(tasks);

		gui.resetTasks(tasks);

		
		
		gui.show();
		
		db.unsetPin("3", "lowerPin");
		
		System.out.println("closed!");
		return;

	}

}
