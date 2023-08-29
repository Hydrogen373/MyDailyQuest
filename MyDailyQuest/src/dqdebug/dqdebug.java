package dqdebug;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dqdatabase.Database;
import dqgui.DqCalendar;
import dqgui.GUIManager;
import dqgui.TaskBox;

public class dqdebug {
	public static void main(String[] args) {
		flexibleTFPage();
	}
	
	static void defaultPage() {
		Database db = new Database();
		db.regenerate("2", "20220202");
		db.removeTask("debug");
		db.addTask("debug", "debugtest05");
		
		
		db.setPin("debug", "highpin");
		db.setPin("2", "highpin");
		db.setPin("2", "lowerPin");
		db.setPin("3", "lowerPin");
		
		GUIManager gui = new GUIManager();
		
		HashMap<String, TaskBox> uidToBox = db.loadAllTask();
		ArrayList<String> uids = db.sort(new ArrayList<String>(uidToBox.keySet()));

		gui.setMap(uidToBox);
		gui.resetTasksPanel(uids);
		
		gui.show();
		
		db.unsetPin("3", "lowerPin");
		
		System.out.println("closed!");
		return;
	}
	
	static void calendarPage() {
		JFrame mainFrame = new JFrame();
		DqCalendar cal = DqCalendar.getInstance();
		mainFrame.add(cal);
		mainFrame.setVisible(true);
		mainFrame.setSize(400,400);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
	}
	
	static void lineEditPage() {
		JFrame mainFrame = new JFrame();
		JPanel mainPanel = new JPanel();
		JLabel label = new JLabel();
		label.setText("this is test label");
		JTextField edit = new JTextField();
		JTextField edit2 = new JTextField();
		
		edit.setPreferredSize(new Dimension(200, 20));
		
		

		class JTextFieldHintListener implements FocusListener {
		    private String hintText;
		    private JTextField textField;
		    public JTextFieldHintListener(JTextField jTextField,String hintText) {
		        this.textField = jTextField;
		        this.hintText = hintText;
		        jTextField.setText(hintText);  //默认直接显示
		        jTextField.setForeground(Color.GRAY);
		    }
		 
		    @Override
		    public void focusGained(FocusEvent e) {
		        String temp = textField.getText();
		        if(temp.equals(hintText)) {
		            textField.setText("");
		            textField.setForeground(Color.BLACK);
		        }
		        
		    }
		 
		    @Override
		    public void focusLost(FocusEvent e) {   
		        String temp = textField.getText();
		        if(temp.equals("")) {
		            textField.setForeground(Color.GRAY);
		            textField.setText(hintText);
		        }
		        
		    }
		 
		}
		
		Action action = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("action");
				
			}
		};
		
		JTextFieldHintListener listener = new JTextFieldHintListener(edit, "this is hint");
		edit.addFocusListener(listener);
		edit.addActionListener(action);

		mainPanel.add(label);
		mainPanel.add(edit);
		mainPanel.add(edit2);

		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
		mainFrame.setSize(400,400);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
	}
	
	static void flexibleTFPage() {
		JFrame mainFrame = new JFrame();
		
		JPanel mainPanel = new JPanel();
		
		JLabel label = new JLabel("TextEdit");
		JTextField tf = new JTextField();
		
		tf.setVisible(false);
		
		mainPanel.add(label);
		mainPanel.add(tf);
		
		ActionListener action = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("action");
				label.setText(tf.getText());
				tf.setVisible(false);
				label.setVisible(true);
			}
		};
		


		MouseListener mouse = new MouseListener() {
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
				
				tf.setText(label.getText());
				label.setVisible(false);
				tf.setVisible(true);
				
			}
		};
		
		label.addMouseListener(mouse);
		tf.addActionListener(action);
		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
		mainFrame.setSize(800, 800);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		
		
		
	}

}
