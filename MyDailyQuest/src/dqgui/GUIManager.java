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
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import dqdatabase.Database;


public class GUIManager {
	JFrame mainFrame = new JFrame();
	JPanel mainPanel = new JPanel();
	ArrayList<TaskBox> tasks = new ArrayList<TaskBox>();
	HashMap<String, TaskBox> uidToBox;
	ArrayList<PinBox> pins = null;
	JPanel pinsPanel = new JPanel();
	JPanel tasksPanel = new JPanel();
	CardLayout cards = new CardLayout();
	
	public GUIManager() {
		mainPanel.setLayout(cards);

		JPanel card1 = new JPanel();
		card1.setLayout(new BorderLayout());
//		card1.setLayout(new GridLayout(2,1));
		
		pinsPanel.setLayout(new FlowLayout());
		Database db = new Database();
		pins = db.loadAllPin();
		for(PinBox pin : pins) {
			pinsPanel.add(pin);
		}

		
		// TODO add menupanel
//		JPanel menuPanel = new JPanel();
		
		JPanel center = new JPanel();
//		tasksPanel.setLayout(new GridLayout(0,1, 10, 10));
		tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
		// TODO db loadAllTask, resetTaskPanel
		tasksPanel.setBorder(new LineBorder(Color.black));
		center.add(tasksPanel);
		
		card1.add(pinsPanel, BorderLayout.NORTH);
		card1.add(tasksPanel, BorderLayout.CENTER);

		mainPanel.add(card1);
		mainFrame.add(mainPanel);

	}
	
	
	public JPanel getTaskBox(String uid, String content) {
		JPanel result = new JPanel();
		
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		
		result.add(new JLabel("UID : " + uid));
		result.add(new JLabel("Content : " + content));
		
		
		return result;
	}
	
	public void show() {
		
		mainFrame.setVisible(true);
		mainFrame.setSize(840, 700);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
	}
	
	public void setMap(HashMap<String, TaskBox> uidToBox) {
		this.uidToBox = uidToBox;
	}
	
	public void resetTasksPanel(ArrayList<String> uids) {
		tasksPanel.removeAll();
		for(String uid : uids) {
			tasksPanel.add(uidToBox.get(uid));
		}
		tasksPanel.add(getAdditionalTask());
	}
	
	JPanel getAdditionalTask() {
		JPanel additionalTask = new JPanel();
		JLabel label = new JLabel("Add New Task");
		JTextField content = new JTextField();
				
		ActionListener action = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("action");
				if(content.getText().strip().equals("")) {
					return;
				}
				String uid = new String(Database.generateUID());
				Database db = new Database();
				db.addTask(uid, content.getText().strip());
				content.setText("");
			}
		};
		
		content.addActionListener(action);
		
		additionalTask.add(label);
		additionalTask.add(content);
		SpringLayout spring = new SpringLayout();
		spring.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, additionalTask);
		spring.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, additionalTask);
		spring.putConstraint(SpringLayout.WEST, content, 5, SpringLayout.EAST, label);
		spring.putConstraint(SpringLayout.NORTH, content, 5, SpringLayout.NORTH, additionalTask);
		spring.putConstraint(SpringLayout.EAST, additionalTask, 5, SpringLayout.EAST, content);
//		spring.putConstraint(SpringLayout.SOUTH, additionalTask, 5, SpringLayout.SOUTH, content);
		additionalTask.setLayout(spring);
		return additionalTask;
	}
	


}