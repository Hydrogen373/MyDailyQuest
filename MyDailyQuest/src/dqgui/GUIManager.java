package dqgui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class GUIManager {
	
	public void test() {
		JFrame mainFrame = new JFrame();
		JPanel mainPanel = new JPanel();
		CardLayout cards = new CardLayout();
		mainPanel.setLayout(cards);

		mainPanel.add(new JLabel("Test GUI. Hello world!!"));
		
		ArrayList<TaskBox> tasks = new ArrayList<TaskBox>();
		tasks.add(new TaskBox("00", "Study English", "20220101", false, null));
		tasks.add(new TaskBox("01", "Study Korean", "20220101", false, null));
		tasks.add(new TaskBox("02", "Study Japanesh", "20220101", false, null));

		JPanel tasksPanel = new JPanel();
		tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.PAGE_AXIS));
		for (TaskBox task : tasks) {
			tasksPanel.add(task.getPanel());
		}
		
		mainPanel.add(tasksPanel);
		
		
		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
		mainFrame.setSize(840, 700);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
//		mainFrame.setResizable(false);
		
		tasks.get(0).modifyContent("this is new content");
		
		cards.next(mainPanel);
		
	}
	
	public JPanel getTaskBox(String uid, String content) {
		JPanel result = new JPanel();
		
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		
		result.add(new JLabel("UID : " + uid));
		result.add(new JLabel("Content : " + content));
		
		
		return result;
	}

}
