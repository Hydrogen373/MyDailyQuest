package dqgui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TaskBox {
	public String uid ="";
//	JLabel content = new JLabel();
	JLabel dateLabel = new JLabel();
	String recent_completion_date;
	String tmp_completion_date;
	public boolean done = false;
	JPanel panel = new JPanel();
	JCheckBox check = new JCheckBox();
	
	final static String DEFAULT_DATE = "00000000";
	
	
	
	public TaskBox(String uid, String content, String recent_completion_date, boolean done, String tmp_completion_date) {
		this.uid = uid;
//		this.content.setText(content);
		this.recent_completion_date = recent_completion_date!=null?recent_completion_date:DEFAULT_DATE;
		this.dateLabel.setText(this.recent_completion_date);
		this.done = done;
		check.setSelected(done);
		this.tmp_completion_date = tmp_completion_date!=null?tmp_completion_date:DEFAULT_DATE;
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setAlignmentX(0);
		panel.add(this.dateLabel);
		panel.add(check);
		check.setText(content);
		
//		//debug
//		panel.add(new JLabel("debug content"));
//		panel.setBackground(Color.green);

		
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	public void modifyContent(String newContent) {
		this.check.setText(newContent);
	}
	
	
	
	
	

}
