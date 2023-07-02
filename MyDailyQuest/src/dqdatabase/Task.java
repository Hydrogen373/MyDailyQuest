package dqdatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Task {
	static long uid_gen = 0;
	String uid = "";
	String content = "";
	Calendar recent_completion_date;
	boolean done = false;
	Calendar tmp_completion_date;

	/*
	 * Task properties
	 * 
	 * regen_rules by day of the week by period by date
	 * 
	 * activation_rules by deadline by day of the week by date
	 * 
	 * Info uid content recent_complete_date done tmp_complete_date
	 */

	/*
	 * TODO Task Attirbutes table
	 * 
	 * like pin function no requirements of group function priority by task
	 * attributes apply radix sort
	 */

	/*
	 * TODO Sorting using radix sort Sort from low priority order Then sort by
	 * completion
	 * 
	 */


	public Task(String _uid, String _content, Calendar _recent_completion_date, boolean _done, Calendar _tmp_completion_date) {
		uid = _uid;
		content= _content;
		recent_completion_date = _recent_completion_date;
		done = _done;
		tmp_completion_date = _tmp_completion_date;
	}

	public boolean isRegeneratable() {
		if (!done) {
			return false;
		}

		//TODO check regeneration rule from DB directly
		

		return false;
	}

	public void regenerate() {
		/*
		 * Regen if done, do regen recent_completion_date = tmp_completion_date
		 */
		done = false;
		if (tmp_completion_date != null) {
			recent_completion_date = tmp_completion_date;
		}
	}
	
	public void checkDone() {
		if (!done) {
			done = true;
			tmp_completion_date = Calendar.getInstance();
		}
		else {
			return;
		}
	}

}
