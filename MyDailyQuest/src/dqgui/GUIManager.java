package dqgui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import dqdatabase.Database;


public class GUIManager {
	JFrame mainFrame = new JFrame();
	JPanel mainPanel = new JPanel();
	ArrayList<TaskBox> tasks = new ArrayList<TaskBox>();
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
			System.out.println("!");
		}

		
//		JPanel menuPanel = new JPanel();
		
		JPanel center = new JPanel();
		tasksPanel.setLayout(new GridLayout(0,1, 10, 10));
		for (TaskBox task : tasks) {
			tasksPanel.add(task);
		}
		tasksPanel.setBorder(new LineBorder(Color.black));
		center.add(tasksPanel);
		
		card1.add(pinsPanel, BorderLayout.NORTH);
		card1.add(center, BorderLayout.CENTER);

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
	
	public void resetTasks(ArrayList<TaskBox> newTasks) {
		tasksPanel.removeAll();
		tasks = newTasks;
		
		for(TaskBox task : tasks) {
			tasksPanel.add(task);
		}
	}
	


}


class MenuPanel extends JPanel{
	MenuPanel(){
		
	}
}