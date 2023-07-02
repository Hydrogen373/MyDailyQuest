package dqdebug;

import java.sql.*;

import dqdatabase.Database;

public class dqdebug {
	public static void main(String[] args) {
		Database db = new Database();
		
		db.show();
		db.regen("3", "20220203");
		db.show();
		return;

	}

}
