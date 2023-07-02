package dqdatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Task {
	static long uid_gen = 0;
	long uid = 0;
	String content = "";
	ArrayList<Boolean> regen_rules = new ArrayList<Boolean>();
	ArrayList<Boolean> activation_rules = new ArrayList<Boolean>();
	Calendar recent_completion_date;
	boolean done = false;
	Calendar tmp_completion_date;
	
	/* todo list
	 * constructor
	 * destructor
	 */
	
	public Task() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
