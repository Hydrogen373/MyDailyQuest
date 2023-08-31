package dqgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dqdatabase.DqDatabase;

public class TaskBox extends JPanel {
	static Font font_date = new Font(null, 0, 15);
	static Font font_content = new Font(null, Font.BOLD, 18);
	static Font font_content_done = new Font(null, Font.ITALIC, 18);

	String taskUID;
	boolean done;
	JLabel dateLabel = new JLabel();
	JLabel checkLabel = new JLabel();
	JLabel contentLabel = new JLabel();
	TaskMouseListener listener = new TaskMouseListener();

	static final ImageIcon ICON_CIRCLE = new ImageIcon("icon/circle.png");
	static final ImageIcon ICON_CHECKED = new ImageIcon("icon/check.png");

	public TaskBox(String uid, String content, String date, boolean done) {
		this.taskUID = uid;
		this.dateLabel.setText(date);
		this.contentLabel.setText(content);
		this.done = done;
		checkLabel.setIcon(this.done ? ICON_CHECKED : ICON_CIRCLE);

		BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(layout);
		this.setBorder(new LineBorder(Color.black));
		this.dateLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		this.dateLabel.setFont(font_date);
		this.contentLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		this.contentLabel.setFont(done ? font_content_done : font_content);
		this.add(this.dateLabel);
		this.add(this.checkLabel);
		this.add(this.contentLabel);
		listener.setBox(this);
		this.addMouseListener(listener);

		int preferredHeight = Integer.max(contentLabel.getPreferredSize().height,
				checkLabel.getPreferredSize().height + 15);
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
		this.setPreferredSize(new Dimension(layout.preferredLayoutSize(this).width, preferredHeight));
		this.setMinimumSize(this.getPreferredSize());

	}

	public String getUID() {
		return this.taskUID;
	}

	public boolean getDone() {
		return this.done;
	}

	public void modifyContent(String newContent) {
		this.contentLabel.setText(newContent);
	}

	public void checkEvent() {
		DqDatabase db = new DqDatabase();
		String result = db.checkDone(getUID(), !getDone());
		if (result != null) {
			this.dateLabel.setText(result);
			this.done = !this.done;
			if (this.done) {
				checkLabel.setIcon(ICON_CHECKED);
				contentLabel.setFont(font_content_done);
			} else {
				checkLabel.setIcon(ICON_CIRCLE);
				contentLabel.setFont(font_content);
			}
		}
	}

}

class TaskMouseListener implements MouseListener {
	boolean ready = false;
	TaskBox taskbox;

	public void setBox(TaskBox taskbox) {
		this.taskbox = taskbox;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!ready)
			return;
		if (e.getButton() == MouseEvent.BUTTON1) {
			taskbox.checkEvent();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		ready = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		ready = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}
}
