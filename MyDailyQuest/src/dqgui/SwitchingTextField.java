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
import javax.swing.Spring;
import javax.swing.SpringLayout;

public class SwitchingTextField extends JPanel {
	JLabel label = null;
	JTextField tf = null;
	MouseListener mouseLabel = null;
	FocusListener focus = null;
	KeyListener keyTf = null;
	ActionListener actionTf = null;
	boolean label_noText = true;

	String defaultText = new String("Add New Task...");

	public SwitchingTextField() {
		label = new JLabel();
		label.setText(defaultText);

		tf = new JTextField();
		mouseLabel = new MouseListener() {
			boolean ready = false;

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
		};
		label.addMouseListener(mouseLabel);

		actionTf = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
					switchToLabel();
					// debug
					System.out.println("keyboard listener: escape");
				}
			}
		};
		tf.addKeyListener(keyTf);

		tf.setVisible(false);
		label.setVisible(true);
		this.add(tf);
		this.add(label);

		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, label);
		layout.putConstraint(SpringLayout.WEST, tf, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, tf, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, this, 5, SpringLayout.EAST, tf);
		layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, tf);
		this.setLayout(layout);

		// debug
		this.setBackground(Color.magenta);

	}
	
	public void switchToLabel() {
		String tmp = new String(tf.getText().strip());
		if (tmp.equals("")) {
			label.setText(defaultText);
			label_noText = true;
		} else {
			label.setText(tmp);
			label_noText = false;
		}
		tf.setVisible(false);
		label.setVisible(true);
	}


}
