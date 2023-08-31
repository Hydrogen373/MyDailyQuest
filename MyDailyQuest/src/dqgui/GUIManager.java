package dqgui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.print.StreamPrintService;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import dqdatabase.DqDatabase;

public class GUIManager {
	JFrame mainFrame = null;
	JPanel mainPanel = null;
	CardLayout pages = null;

	HashMap<String, TaskBox> uid2task = null;
	JPanel tasksPanel = null;
	ArrayList<String> sortedUids = null;

	// TODO replace with HashMap pinName2pinPanel
	ArrayList<PinBox> pins = null;
	JPanel pinsPanel = new JPanel();

	public GUIManager() {
		mainFrame = new JFrame();
		mainPanel = new JPanel();
		pages = new CardLayout();

		mainPanel.setLayout(pages);
		JPanel page1 = new JPanel();
		initPage1(page1);

		mainPanel.add(page1);
		mainFrame.add(mainPanel);
	}

	private void initPage1(JPanel page1) {
		page1.setLayout(new BorderLayout());

		DqDatabase db = new DqDatabase();

		// page1 north
			// init pinsPanel
			pinsPanel.setLayout(new FlowLayout());
			pins = db.loadAllPin();
			for (PinBox pin : pins) {
				pinsPanel.add(pin);
			}
			// TODO add menupanel

		// page1 center
			// init taskPanel
				// init uid2task
				uid2task = db.loadAllTask();
				// init sortedUids
				sortUids();
			tasksPanel = new JPanel();
			tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
			// XXX debug
			tasksPanel.setBorder(new LineBorder(Color.black));
			resetTasksPanel(sortedUids);
		JScrollPane center = new JScrollPane(tasksPanel);

		// page1 south
			// TODO addTask to db using additionalTaskpanel
			SwitchingTextField additionalTaskPanel = new SwitchingTextField();

		page1.add(pinsPanel, BorderLayout.NORTH);
		page1.add(center, BorderLayout.CENTER);
		page1.add(additionalTaskPanel, BorderLayout.SOUTH);
	}

	public void show() {
		mainFrame.setVisible(true);
		mainFrame.setSize(425, 600);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
	}

	public void setMap(HashMap<String, TaskBox> uidToBox) {
		this.uid2task = uidToBox;
	}

	void resetTasksPanel(ArrayList<String> uids) {
		tasksPanel.removeAll();
		for (String uid : uids) {
			tasksPanel.add(uid2task.get(uid));
		}
	}
	
	void sortUids() {
		DqDatabase db = new DqDatabase();
		this.sortedUids = db.sort(new ArrayList<String>(uid2task.keySet()));
	}
	
	public void refresh_taskPanel() {
		sortUids();
		resetTasksPanel(sortedUids);
	}



}