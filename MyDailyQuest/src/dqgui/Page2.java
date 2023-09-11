package dqgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import dqdatabase.DqDatabase;

public class Page2 extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	BorderLayout layoutThis = null;

	// center
	JPanel center = null;
	SpringLayout layoutCenter = null;
	JTextField taskName = null;
	DqHighlightingTextArea taskMemo = null;
	// south
	JPanel south = null;
	JButton buttonSave = null;
	JButton buttonCancel = null;

	Page2() {
		layoutThis = new BorderLayout();
		this.setLayout(layoutThis);

		// center
		center = new JPanel();

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

		layoutCenter = new SpringLayout();
		layoutCenter.putConstraint(SpringLayout.HORIZONTAL_CENTER, calendar, 0, SpringLayout.HORIZONTAL_CENTER, center);
		layoutCenter.putConstraint(SpringLayout.NORTH, taskName, 5, SpringLayout.NORTH, center);
		layoutCenter.putConstraint(SpringLayout.NORTH, calendar, 5, SpringLayout.SOUTH, taskName);
		layoutCenter.putConstraint(SpringLayout.NORTH, taskMemo.getTextArea(), 5, SpringLayout.SOUTH, calendar);
		layoutCenter.putConstraint(SpringLayout.SOUTH, center, 5, SpringLayout.SOUTH, taskMemo.getTextArea());
		layoutCenter.putConstraint(SpringLayout.WEST, taskName, 5, SpringLayout.WEST, center);
		layoutCenter.putConstraint(SpringLayout.EAST, taskName, -5, SpringLayout.EAST, center);
		layoutCenter.putConstraint(SpringLayout.WEST, taskMemo.getTextArea(), 5, SpringLayout.WEST, center);
		layoutCenter.putConstraint(SpringLayout.EAST, taskMemo.getTextArea(), -5, SpringLayout.EAST, center);
		center.setLayout(layoutCenter);
		
		south = new JPanel();
		south.setLayout(new FlowLayout());

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

		south.add(buttonSave);
		south.add(buttonCancel);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
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
