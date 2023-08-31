package dqgui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SwitchingTextField extends JPanel {
	JLabel label = null;
	JTextField tf = null;
	CustomMouseListener mouseLabel = null;
	FocusListener focus = null;
	KeyListener keyTf = null;
	ActionListener actionTf = null;
	boolean label_noText = true;
	String stripped = null;

	String defaultText = new String("Add New Task...");

	public SwitchingTextField(GUIManager guiManager) {
		label = new JLabel();
		label.setText(defaultText);
		// TODO set label more big
		// XXX debug
			label.setBackground(Color.cyan);
			label.setOpaque(true);

		tf = new JTextField();
		mouseLabel = new CustomMouseListener();
		label.addMouseListener(mouseLabel);

		actionTf = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stripTf();
				if (!label_noText) {
					GUIManager.addTask(stripped);
					label_noText = true;
				}
				switchToLabel();
			}
		};
		tf.addActionListener(actionTf);

		keyTf = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					stripTf();
					switchToLabel();
					// XXX debug
					System.out.println("keyboard listener: escape");
				}
			}
		};
		tf.addKeyListener(keyTf);

		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, label, -5, SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.WEST, tf, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, tf, -5, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH, tf, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, tf, -5, SpringLayout.SOUTH, this);

		layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, label);
		this.setLayout(layout);

		// XXX debug
		this.setBackground(Color.magenta);

		tf.setVisible(false);
		label.setVisible(true);
		this.add(tf);
		this.add(label);
	}

	public void switchToLabel() {
		if (label_noText) {
			label.setText(defaultText);
		} else {
			label.setText(stripped);
		}
		tf.setVisible(false);
		label.setVisible(true);
	}

	public void stripTf() {
		stripped = tf.getText().strip();
		if (stripped.equals("")) {
			label_noText = true;
		} else {
			label_noText = false;
		}
	}

	public boolean isMouseInThis() {
		return this.mouseLabel.ready;
	}

	class CustomMouseListener implements MouseListener {

		public boolean ready = false;

		@Override
		public void mouseReleased(MouseEvent e) {
			if (!ready) {
				return;
			}
			if (label_noText) {
				tf.setText("");
			} else {
				tf.setText(label.getText());
			}
			label.setVisible(false);
			tf.setVisible(true);
			tf.requestFocusInWindow();
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
	}

}
