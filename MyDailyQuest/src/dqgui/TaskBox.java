package dqgui;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TaskBox {
	String uid ="";
	JLabel content = new JLabel();
	JLabel recent_completion_date = new JLabel();
	String tmp_completion_date;
	boolean done = false;
	JPanel panel = new JPanel();
	JCheckBox check = new JCheckBox();
	
	
	public TaskBox(String uid, String content, String recent_completion_date, boolean done, String tmp_completion_date) {
		this.uid = uid;
		this.content.setText(content);
		this.recent_completion_date.setText(recent_completion_date);
		this.done = done;
		check.setSelected(done);
		this.tmp_completion_date = tmp_completion_date;
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(check);
		panel.add(this.recent_completion_date);
		panel.add(this.content);
		
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	public void modifyContent(String newContent) {
		this.content.setText(newContent);
	}
	
	
	

}
