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
	private static final long serialVersionUID = 2L;
	private static DqCalendar instance;
	static HashMap<Integer, Boolean> changeMap = null;
	static HashMap<Integer, DqCalendarCell> day2cell = null;
	static String taskId = null;

	private DqCalendar() {
	}

	public static DqCalendar getInstance() {
		if (instance == null) {
			instance = new DqCalendar();

//			weekCells = new ArrayList<>();
//			dayCells = new ArrayList<>();
			day2cell = new HashMap<Integer, DqCalendarCell>();
			changeMap = new HashMap<>();

			instance.setLayout(new GridLayout(6, 7, 5, 5));
			{
				day2cell.put(DqDay.sun, new DqCalendarCell("S", DqDay.sun));
				day2cell.put(DqDay.mon, new DqCalendarCell("M", DqDay.mon));
				day2cell.put(DqDay.tue, new DqCalendarCell("T", DqDay.tue));
				day2cell.put(DqDay.wed, new DqCalendarCell("W", DqDay.wed));
				day2cell.put(DqDay.thu, new DqCalendarCell("T", DqDay.thu));
				day2cell.put(DqDay.fri, new DqCalendarCell("F", DqDay.fri));
				day2cell.put(DqDay.sat, new DqCalendarCell("S", DqDay.sat));
			}
			for (int i = 1; i < 32; i++) {
				day2cell.put(i, new DqCalendarCell(Integer.toString(i), i));
			}

			for(int i=32;i<39;i++) {
				instance.add(day2cell.get(i));
			}
			for (int i=1;i<32;i++) {
				instance.add(day2cell.get(i));
			}
			
		} else {

//			for (DqCalendarCell cell : weekCells) {
//				cell.init();
//			}
//			for (DqCalendarCell cell : dayCells) {
//				cell.init();
//			}
			for (Integer i : day2cell.keySet()) {
				day2cell.get(i).init();
			}
			changeMap.clear();

		}
		return instance;
	}

	public static void setActive(ArrayList<Integer> active) {
		for (Integer i : active) {
			day2cell.get(i).checkEvent();
			
		}
	}
	
	public static void setUid(String uid){
		taskId = uid;
	}
	
	public static HashMap<Integer, Boolean> getChangeMap() {
		return changeMap;
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
		this.setBackground(Color.white);
		
		this.id = id;
	}

	boolean ready = false;

	public void init() {
		if (check) {
			checkEvent();
		}
	}

	public void checkEvent() {
		check = !check;
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
			if (DqCalendar.changeMap.get(id) == null) {
				DqCalendar.changeMap.put(id, !check);
			}
			else {
				DqCalendar.changeMap.remove(id);
			}
			this.checkEvent();
			
//			XXX debug
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
