package dqgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dqdatabase.DqDatabase;

public class Page2 extends JPanel {
	BorderLayout layoutThis = null;

	//center
	JPanel center = null;
	BoxLayout layoutCenter = null;
	JTextField taskName = null;
	DqHighlightingTextArea taskMemo = null;
	//south
	JButton buttonSave= null;
	JButton buttonCancel = null;

	private Page2() {
		layoutThis = new BorderLayout();
		this.setLayout(layoutThis);

		// center
		center = new JPanel();

		layoutCenter = new BoxLayout(center, BoxLayout.Y_AXIS);
		center.setLayout(layoutCenter);

		// taskName
		taskName = new JTextField();
		taskName.setMaximumSize(
				new Dimension(DqCalendar.getInstance().getPreferredSize().width, taskName.getPreferredSize().height));
		// calendar
		DqCalendar calendar = DqCalendar.getInstance();
		calendar.setMaximumSize(calendar.getPreferredSize());
		DqHighlightingTextArea taskMemo = new DqHighlightingTextArea();
//		taskMemo.getTextArea().setMaximumSize(new Dimension(DqCalendar.getInstance().getPreferredSize().width, Integer.MAX_VALUE));
//		taskMemo.getTextArea().setMinimumSize(new Dimension(DqCalendar.getInstance().getPreferredSize().width, 0));
		center.add(taskName);
		center.add(calendar);
		center.add(taskMemo.getTextArea());

		buttonSave = new JButton();
		buttonSave.setText("save");
		buttonSave.addMouseListener(new MouseListener() {
			boolean ready = false;

			@Override
			public void mouseReleased(MouseEvent e) {
				if (!ready) {
					return;
				}
				save();
				GUIManager.nextPage();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ready = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ready = true;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		buttonCancel = new JButton();
		buttonCancel.setText("cancel");
		buttonCancel.addMouseListener(new MouseListener() {
			boolean ready = false;

			@Override
			public void mouseReleased(MouseEvent e) {
				if (!ready) {
					return;
				}
				GUIManager.nextPage();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ready = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ready = true;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		this.add(center, BorderLayout.CENTER);
		this.add(buttonSave, BorderLayout.SOUTH);
		this.add(buttonCancel, BorderLayout.SOUTH);
	}

	public void save() {
		HashMap<Integer, Boolean> map = DqCalendar.getChangeMap();

		ArrayList<String> active = new ArrayList<>(), deactive = new ArrayList<>();
		for (int rule : map.keySet()) {
			if (map.get(rule)) {
				active.add(Integer.toString(rule));
			} else {
				deactive.add(Integer.toString(rule));
			}
		}

		DqDatabase db = new DqDatabase();
		db.addRegenRule(DqCalendar.taskId, active);
		db.removeRegenRule(DqCalendar.taskId, deactive);

		// TODO db.changeTags
		
	}
	public void cancel() {
		// TODO
		
	}
}
