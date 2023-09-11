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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.print.StreamPrintService;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.sqlite.core.DB;

import dqdatabase.DqDatabase;
import dqgui.Page2;

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

	static JButton refreshButton = new JButton();

	private GUIManager() {
		mainFrame = new JFrame();
		mainFrame.setSize(425, 600);
		// TODO set frame title and icon

		mainPanel = new JPanel();
		pages = new CardLayout();

		mainPanel.setLayout(pages);

		JPanel page1 = new JPanel();
		initPage1(page1);

		Page2 page2 = new Page2();
//		initPage2(page2);

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

	static public void start() {
		getInstance();
		show();
	}

	static private void initPage1(JPanel page1) {
		page1.setLayout(new BorderLayout());

		DqDatabase db = new DqDatabase();
		db.regenAll();

		// page1 north
		// init pinsPanel
		FlowLayout layout_pinsPanel = new FlowLayout(); 
		// TODO It would be better to use #tag feature rather than pin feature. 
		pinsPanel.setLayout(layout_pinsPanel);
		pins = db.loadAllPin();
		test();pinsPanel.add(refreshButton);// XXX debug
		for (PinBox pin : pins) {
			pinsPanel.add(pin);
		}
		pinsPanel.setBackground(Color.cyan); // XXX
		// TODO set layout be able to show every pin or '...' button
		// Can change north size by setting preferredSize
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

	static private void show() {
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
		DqDatabase db = new DqDatabase();
		tasksPanel.setVisible(false);
		tasksPanel.removeAll();
		db.regenAll();
		uid2task = db.loadAllTask();
		sortUids();
		resetTasksPanel(sortedUids);
		tasksPanel.setVisible(true);
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

	static public void test() {
		refreshButton.setText("refresh");
		refreshButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				GUIManager.refresh_taskPanel();
			}
		});
	}

}