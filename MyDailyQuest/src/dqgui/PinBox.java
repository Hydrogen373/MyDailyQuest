package dqgui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dqdatabase.DqDatabase;

public class PinBox extends JPanel implements MouseListener {
	static final ImageIcon ICON_CIRCLE = new ImageIcon("icon/circle.png");
	static final ImageIcon ICON_CHECKED = new ImageIcon("icon/check.png");
	JLabel pinName = null;
	JLabel activationLabel = null;
	boolean activation = false;

	public PinBox() {

	}

	public PinBox(String name, boolean activation) {
		activationLabel = new JLabel();
		this.activation = activation;
		activationLabel.setIcon(this.activation ? ICON_CHECKED : ICON_CIRCLE);

		pinName = new JLabel();
		pinName.setText(name);
		
		this.add(activationLabel);
		this.add(pinName);
		this.addMouseListener(this);
	}

	boolean mouseInside = false;

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!mouseInside)
			return;
		if (e.getButton() == MouseEvent.BUTTON1) {
			DqDatabase db = new DqDatabase();
			if(db.activePin(pinName.getText(), !this.activation)) {
				this.activation = !this.activation;
				activationLabel.setIcon(this.activation ? ICON_CHECKED : ICON_CIRCLE);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInside = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInside = false;

	}

}
