package dqdebug;

import java.sql.*;

import dqdatabase.Database;
import dqgui.GUIManager;

public class dqdebug {
	public static void main(String[] args) {
		Database db = new Database();
		
//		db.show();
//		db.regenerate("3", "20220203");
//		db.show();
		
		GUIManager gui = new GUIManager();
		gui.test();
		
		return;

	}

}
