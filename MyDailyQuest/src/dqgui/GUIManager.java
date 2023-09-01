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

import org.sqlite.core.DB;

import dqdatabase.DqDatabase;

public class GUIManager {
	static GUIManager instance = null;

	static JFrame mainFrame = null;
	static JPanel mainPanel = null;
	static CardLayout pages = null;

	static HashMap<String, TaskBox> uid2task = null;
	static JPanel tasksPanel = null;
	static ArrayList<String> sortedUids = null;

	// TODO replace with HashMap pinName2pinPanel
	static ArrayList<PinBox> pins = null;
	static JPanel pinsPanel = new JPanel();

	private GUIManager() {
		mainFrame = new JFrame();
		mainFrame.setSize(425, 600);

		mainPanel = new JPanel();
		pages = new CardLayout();

		mainPanel.setLayout(pages);

		JPanel page1 = new JPanel();
		initPage1(page1);

		JPanel page2 = new JPanel();
		initPage2(page2);

		mainPanel.add(page1);
		mainPanel.add(page2);
		mainFrame.add(mainPanel);
	}

	static public GUIManager getInstance() {
		if (instance == null) {
			instance = new GUIManager();
		}

		return instance;
	}

	static public void init() {
		getInstance();
		show();
	}

	static private void initPage1(JPanel page1) {
		page1.setLayout(new BorderLayout());

		DqDatabase db = new DqDatabase();

		// page1 north
		// init pinsPanel
		FlowLayout layout_pinsPanel = new FlowLayout();
		pinsPanel.setLayout(layout_pinsPanel);
		pins = db.loadAllPin();
		for (PinBox pin : pins) {
			pinsPanel.add(pin);
		}
		pinsPanel.setBackground(Color.cyan); // XXX
		// TODO set layout be able to show every pin or '...' button
		// TODO add menupanel

		// page1 center
		// init taskPanel
		// init uid2task
		uid2task = db.loadAllTask();
		// init sortedUids
		sortUids();
		tasksPanel = new JPanel();
		tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
		tasksPanel.setBorder(new LineBorder(Color.black)); // XXX debug
		resetTasksPanel(sortedUids);
		JScrollPane center = new JScrollPane(tasksPanel);

		// page1 south
		// TODO addTask to db using additionalTaskpanel
		SwitchingTextField additionalTaskPanel = new SwitchingTextField(instance);

		page1.add(pinsPanel, BorderLayout.NORTH);
		page1.add(center, BorderLayout.CENTER);
		page1.add(additionalTaskPanel, BorderLayout.SOUTH);
	}

	static private void initPage2(JPanel page2) {
		page2.setLayout(new BorderLayout());

		// center
		JPanel center = new JPanel();
		// calendar
		DqCalendar calendar = DqCalendar.getInstance();
		// TODO pins
		center.add(calendar);

		page2.add(center, BorderLayout.CENTER);
	}

	static public void show() {
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
	}

	static public void setMap(HashMap<String, TaskBox> uidToBox) {
		uid2task = uidToBox;
	}

	static void resetTasksPanel(ArrayList<String> uids) {
		tasksPanel.removeAll();
		for (String uid : uids) {
			tasksPanel.add(uid2task.get(uid));
		}
	}

	static void sortUids() {
		DqDatabase db = new DqDatabase();
		sortedUids = db.sort(new ArrayList<String>(uid2task.keySet()));
	}

	static public void refresh_taskPanel() {
		sortUids();
		resetTasksPanel(sortedUids);
	}

	static public void addTask(String content) {
		String taskId = DqDatabase.generateUID();

		DqDatabase db = new DqDatabase();
		if (!db.addTask(taskId, content)) {
			// Database fail
			return;
		}
		TaskBox taskBox = new TaskBox(taskId, content, DqDatabase.DEFAULT_DATE, false);
		uid2task.put(taskId, taskBox);
		tasksPanel.add(taskBox);
	}

	static public void removeTask(String taskId) {
		if (!uid2task.keySet().contains(taskId)) {
			// fail
			return;
		}
		DqDatabase db = new DqDatabase();
		db.removeTask(taskId);
		uid2task.get(taskId).setVisible(false);
		tasksPanel.remove(uid2task.get(taskId));
	}

	static public void nextPage() {
		pages.next(mainPanel);
	}

	public void test() {
		mainFrame.setSize(739 + 10, 739);
		System.out.println("GUIManager test");
		for (int i = 0; i < pins.size(); i++) {
			System.out.println("pin [" + i + "]: " + (pins.get(i).isVisible() ? "visible" : "invisible"));
		}

		Dimension min = pinsPanel.getLayout().minimumLayoutSize(pinsPanel);
		Dimension pre = pinsPanel.getLayout().preferredLayoutSize(pinsPanel);
		Dimension now = pinsPanel.getSize();
		Dimension noww = mainFrame.getSize();

		System.out.println("min " + min.width + "\npre " + pre.width + "\nnow " + now.width);
		System.out.println("noww " + noww.width);
		FlowLayout layout = (FlowLayout) pinsPanel.getLayout();
		System.out.println(layout.getHgap());
	}

}