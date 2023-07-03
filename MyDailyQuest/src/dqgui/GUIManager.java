package dqgui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIManager {
	
	public void test() {
		JFrame mainFrame = new JFrame();
		
		JPanel panel = new JPanel();

		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JLabel("Test GUI. Hello world!!"));
		TaskBox task0 = new TaskBox("00", "Study English", "20220101", false, null);
		panel.add(task0.getPanel());
		
		mainFrame.add(panel);
		
		
		mainFrame.setVisible(true);
		mainFrame.setSize(840, 700);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
//		mainFrame.setResizable(false);
		
	}
	
	public JPanel getTaskBox(String uid, String content) {
		JPanel result = new JPanel();
		
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		
		result.add(new JLabel("UID : " + uid));
		result.add(new JLabel("Content : " + content));
		
		
		return result;
	}

}
