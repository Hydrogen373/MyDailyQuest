package dqdebug;

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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dqdatabase.DqDatabase;
import dqgui.DqCalendar;
import dqgui.GUIManager;
import dqgui.SwitchingTextField;
import dqgui.TaskBox;

public class dqdebug {
	static JFrame mainFrame = null;

	public static void main(String[] args) {
		defaultPage();
	}

	static void defaultPage() {
		GUIManager gui = GUIManager.getInstance();

		gui.start();

		System.out.println("closed!");
		return;
	}

	static void calendarPage() {
		JFrame mainFrame = new JFrame();
		DqCalendar cal = DqCalendar.getInstance();
		mainFrame.add(cal);
		mainFrame.setVisible(true);
		mainFrame.setSize(400, 400);
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

			public JTextFieldHintListener(JTextField jTextField, String hintText) {
				this.textField = jTextField;
				this.hintText = hintText;
				jTextField.setText(hintText); // 默认直接显示
				jTextField.setForeground(Color.GRAY);
			}

			@Override
			public void focusGained(FocusEvent e) {
				String temp = textField.getText();
				if (temp.equals(hintText)) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				String temp = textField.getText();
				if (temp.equals("")) {
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
		mainFrame.setSize(400, 400);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
	}

	static void flexibleTFPage() {
		JFrame mainFrame = new JFrame();
		MouseListener fraeListener = new MouseListener() {

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
				System.out.println("frame clicked");

			}
		};

		JPanel mainPanel = new JPanel();
//		mainPanel.setFocusable(true);
		MouseListener panelListener = new MouseListener() {

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
				System.out.println("panel clicked");
				mainPanel.requestFocusInWindow();
			}
		};
		mainPanel.addMouseListener(panelListener);

		JLabel label = new JLabel("TextEdit");
		JTextField tf = new JTextField();

		tf.setVisible(false);

		mainPanel.add(label);
		mainPanel.add(tf);

		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(tf.getText());
				tf.setVisible(false);
				label.setVisible(true);

			}
		};

		FocusListener tfFocusListener = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("focus lost");
				action.actionPerformed(null);
			}

			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("focus gained");

			}
		};

		tf.addFocusListener(tfFocusListener);

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
				tf.requestFocusInWindow();

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

	static void setup() {
		mainFrame = new JFrame();
	}

	static void show() {
		mainFrame.setVisible(true);
		mainFrame.setSize(800, 800);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
	}

	static void scrollboxPage() {

		setup();

		DqDatabase db = new DqDatabase();
		HashMap<String, TaskBox> uidToTaskbox = db.loadAllTask();

		JPanel taskPane = new JPanel();
		BoxLayout layout_taskPane = new BoxLayout(taskPane, BoxLayout.Y_AXIS);
//		GridLayout layout_taskPane = new GridLayout(0, 1);
		taskPane.setLayout(layout_taskPane);
		for (String key : uidToTaskbox.keySet()) {
			taskPane.add(uidToTaskbox.get(key));
		}
//		taskPane.setPreferredSize(new Dimension(0, 10000));
		taskPane.setPreferredSize(layout_taskPane.preferredLayoutSize(taskPane));
		taskPane.setBorder(BorderFactory.createLineBorder(Color.red));

		JScrollPane scroll = new JScrollPane(taskPane);

		mainFrame.add(scroll);
		mainFrame.pack();
		show();
	}

	static void popupmenuPage() {
		setup();

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.magenta);

		JPopupMenu pop = new JPopupMenu("menu");
		JMenuItem menuItem = new JMenuItem("item");
		menuItem.setBackground(Color.cyan);
		pop.add(menuItem);

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
				System.out.println("mouse clicked!");
				pop.show(mainPanel, e.getX(), e.getY());
			}
		};

		mainPanel.addMouseListener(mouse);
		mainPanel.add(pop);
		mainFrame.add(mainPanel);
		show();
	}

	static void dateformat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTime().toString());
		String dateStr = sdf.format(cal.getTime());
		System.out.println(dateStr);
		Date dateReverse = new Date();
		try {
			dateReverse = sdf.parse(dateStr);
			System.out.println(dateReverse.getDay());
			System.out.println(dateReverse);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
