package dqgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class DqCalendar extends JPanel {
	private static DqCalendar instance;
	static ArrayList<DqCalendarCell> weekCells = null;
	static ArrayList<DqCalendarCell> dayCells = null;
	static HashMap<Integer, Boolean> changeMap = null;

	private DqCalendar() {
	}

	public static DqCalendar getInstance() {
		if (instance == null) {
			instance = new DqCalendar();

			weekCells = new ArrayList<>();
			dayCells = new ArrayList<>();
			changeMap = new HashMap<>();

			instance.setLayout(new GridLayout(6, 7, 5, 5));
			{
				weekCells.add(new DqCalendarCell("S", DqDay.sun));
				weekCells.add(new DqCalendarCell("M", DqDay.mon));
				weekCells.add(new DqCalendarCell("T", DqDay.tue));
				weekCells.add(new DqCalendarCell("W", DqDay.wed));
				weekCells.add(new DqCalendarCell("T", DqDay.thu));
				weekCells.add(new DqCalendarCell("F", DqDay.fri));
				weekCells.add(new DqCalendarCell("S", DqDay.sat));
			}
			for (int i = 1; i < 32; i++) {
				dayCells.add(new DqCalendarCell(Integer.toString(i), i));
			}

			for (DqCalendarCell cell : weekCells) {
				instance.add(cell);
			}
			for (DqCalendarCell cell : dayCells) {
				instance.add(cell);
			}
			
		} else {

			for (DqCalendarCell cell : weekCells) {
				cell.init();
			}
			for (DqCalendarCell cell : dayCells) {
				cell.init();
			}
			changeMap.clear();

		}
		return instance;
	}

}

class DqDay {
	public static int sun = 32;
	public static int mon = 33;
	public static int tue = 34;
	public static int wed = 35;
	public static int thu = 36;
	public static int fri = 37;
	public static int sat = 38;
}

class DqCalendarCell extends JLabel implements MouseListener {
	static Font font = new Font(null, Font.BOLD, 18);
	boolean check = false;
	int id;

	DqCalendarCell(String content, int id) {
		this.setText(content);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBorder(new LineBorder(new Color(0)));
		this.setFont(font);
		this.addMouseListener(this);
		this.setOpaque(true);
		
		this.id = id;
	}

	boolean ready = false;

	public void init() {
		if (check) {
			check = false;
			checkEvent();
		}
	}

	public void checkEvent() {
		if (check) {
			this.setBackground(Color.cyan);
		} else {
			this.setBackground(Color.white);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!ready)
			return;
		if (e.getButton() == MouseEvent.BUTTON1) {
			check = !check;
			if (DqCalendar.changeMap.get(id) == null) {
				DqCalendar.changeMap.put(id, check);
			}
			else {
				DqCalendar.changeMap.remove(id);
			}
			this.checkEvent();
			
//			debug
			{
				System.out.println("#################################");
				for(Integer id : DqCalendar.changeMap.keySet()) {
					System.out.println("id: "+id+" | "+(DqCalendar.changeMap.get(id)?"Active" : "Deactive"));
				}
				System.out.println("#################################\n\n");
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		ready = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		ready = false;
	}

}
