package dqgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import dqdatabase.Database;

public class TaskBox extends JPanel implements MouseListener {
	TaskData data = new TaskData();
	JLabel dateLabel = new JLabel();
	JLabel checkLabel = new JLabel();
	JLabel contentLabel = new JLabel();

	static final ImageIcon ICON_CIRCLE = new ImageIcon("icon/circle.png");
	static final ImageIcon ICON_CHECKED = new ImageIcon("icon/check.png");

	public TaskBox(String uid, String content, String recent_completion_date, boolean done,
			String tmp_completion_date) {
		this.data.uid = uid;
		this.data.recent_completion_date = recent_completion_date != null ? recent_completion_date
				: TaskData.DEFAULT_DATE;
		this.dateLabel.setText(done ? tmp_completion_date : recent_completion_date);
		this.data.done = done;
		this.data.tmp_completion_date = tmp_completion_date != null ? tmp_completion_date : TaskData.DEFAULT_DATE;
		this.data.content = content;

		checkLabel.setIcon(this.data.done ? ICON_CHECKED : ICON_CIRCLE);
		checkLabel.setSize(1, 1);

		contentLabel.setText(this.data.content);

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(new LineBorder(Color.black));
		this.dateLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		this.contentLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		this.add(this.dateLabel);
		this.add(this.checkLabel);
		this.add(this.contentLabel);
		this.addMouseListener(this);

		this.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				Integer.max(contentLabel.getPreferredSize().height, checkLabel.getPreferredSize().height + 15)));
	}

	public String getUID() {
		return this.data.uid;
	}

	public boolean getDone() {
		return this.data.done;
	}

	public void modifyContent(String newContent) {
		this.data.content = newContent;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	boolean ready = false;

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!ready)
			return;
		if (e.getButton() == MouseEvent.BUTTON1) {
//			System.out.println("check!");
			Database db = new Database();
			String result = db.checkDone(getUID(), !getDone());
			if (result != null) {
				this.dateLabel.setText(result);
				this.data.done = !this.data.done;
				if (this.data.done) {
					checkLabel.setIcon(ICON_CHECKED);
				} else {
					checkLabel.setIcon(ICON_CIRCLE);
				}

//				System.out.println(result);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println("mouseEntered");
		ready = true;

	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.println("mouseExit");
		ready = false;

	}

}

class TaskData {
	final static String DEFAULT_DATE = "00000000";
	final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	public String uid;
	public String recent_completion_date = DEFAULT_DATE;
	public String tmp_completion_date = DEFAULT_DATE;
	public boolean done = false;
	public String content;

	public boolean checkDone() {
		boolean result = false;
		Database db = new Database();
		Calendar cal = Calendar.getInstance();
		String newDate = sdf.format(cal.getTime());

		if (db.checkDone(this.uid, !this.done, newDate)) {
			this.done = !done;
			tmp_completion_date = newDate;
			result = true;
		}

		return result;
	}
}