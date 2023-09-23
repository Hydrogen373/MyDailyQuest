package dqdatabase;

import java.util.HashSet;

import dqgui.TaskBox;

public class DqTask {
	TaskData data = new TaskData();
	TaskBox taskBox = null;
	


	static class TaskData {
		public String taskId = null;
		public HashSet<String> tags = null;
	}
}
