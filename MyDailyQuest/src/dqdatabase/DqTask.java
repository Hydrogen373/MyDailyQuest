package dqdatabase;

import java.util.HashSet;

import dqgui.TaskBox;

public class DqTask {
	TaskData data = new TaskData();
	TaskBox taskBox = null;
	


	static class TaskData {
		String taskId = null;
		HashSet<String> tags = null;
	}
}
