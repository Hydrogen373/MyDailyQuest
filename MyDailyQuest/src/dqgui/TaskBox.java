package dqgui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dqdatabase.Database;

//public class TaskBox {
//	public String uid = "";
////	JLabel content = new JLabel();
//	JLabel dateLabel = new JLabel();
//	String recent_completion_date;
//	String tmp_completion_date;
//	public boolean done = false;
//	JPanel panel = new JPanel();
//	JCheckBox check = new JCheckBox();
//
//	final static String DEFAULT_DATE = "00000000";
//
//	public TaskBox(String uid, String content, String recent_completion_date, boolean done,
//			String tmp_completion_date) {
//		this.uid = uid;
////		this.content.setText(content);
//		this.recent_completion_date = recent_completion_date != null ? recent_completion_date : DEFAULT_DATE;
//		this.dateLabel.setText(this.recent_completion_date);
//		this.done = done;
//		check.setSelected(done);
//		this.tmp_completion_date = tmp_completion_date != null ? tmp_completion_date : DEFAULT_DATE;
//
//		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//		panel.setAlignmentX(0);
//		panel.add(this.dateLabel);
//		panel.add(check);
//		check.setText(content);
//
////		//debug
////		panel.add(new JLabel("debug content"));
////		panel.setBackground(Color.green);
//
//	}
//
//	public JPanel getPanel() {
//		return this.panel;
//	}
//
//	public void modifyContent(String newContent) {
//		this.check.setText(newContent);
//	}
//
//}

public class TaskBox extends JPanel implements MouseListener {
	TaskData data = new TaskData();
	JLabel dateLabel = new JLabel();
	JCheckBox check = new JCheckBox();
	
	public TaskBox(String uid, String content, String recent_completion_date, boolean done,
			String tmp_completion_date) {
		this.data.uid = uid;
		this.data.recent_completion_date = recent_completion_date != null ? recent_completion_date : TaskData.DEFAULT_DATE;
		this.dateLabel.setText(done?tmp_completion_date:recent_completion_date);
		this.data.done = done;
		this.data.tmp_completion_date = tmp_completion_date != null ? tmp_completion_date : TaskData.DEFAULT_DATE;
		this.data.content = content;

		check.setSelected(done);
		check.setText(data.content);
		check.addMouseListener(this);
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setAlignmentX(0);
		this.add(this.dateLabel);
		this.add(check);
	}
	
	public String getUID() {
		return this.data.uid;
	}
	
	public boolean getDone() {
		return this.check.isSelected();
	}
	
	public void modifyContent(String newContent) {
		this.data.content = newContent;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			System.out.println("check!");
			Database db = new Database();
			String result = db.checkDone(getUID(), getDone());
			if(result != null) {
				this.dateLabel.setText(result);
				System.out.println(result);
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}

class TaskData {
	final static String DEFAULT_DATE= "00000000";
	final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	public String uid;
	public String recent_completion_date = DEFAULT_DATE;
	public String tmp_completion_date = DEFAULT_DATE;
	public boolean done = false;
	public String content;
	
	public boolean checkDone() {
		boolean result =false;
		Database db = new Database();
		Calendar cal = Calendar.getInstance();
		String newDate = sdf.format(cal.getTime());
		
		
		if(db.checkDone(this.uid, !this.done, newDate)) {
			this.done = !done;
			tmp_completion_date = newDate;
			result = true;
		}
		
		return result;
	}
}