package dqdebug;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class DqDebugHighlight {
	public JTextArea ta = new JTextArea();
	Highlighter highlighter = ta.getHighlighter();
	HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);

//	String pattern = "(#\\w{1,}[\\s$]){0,}";

	public void highlighting() {
		int startIndex = 0, endIndex = 0;
		Highlighter highlighter = ta.getHighlighter();
		highlighter.removeAllHighlights();
		while (true) {
			Pattern pattern = Pattern.compile("#[\\w|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+\\b");
//			pattern = Pattern.compile("#");
			Matcher matcher = pattern.matcher(ta.getText());

			if (matcher.find(endIndex)) {
				startIndex = matcher.start();
				endIndex = matcher.end();
			}
			else {
				System.out.println("Not matched!");
				return;
			}

			if (startIndex == -1) {
				return;
			}

			System.out.println(startIndex + " ~ " + endIndex);

			try {
				highlighter.addHighlight(startIndex, endIndex, painter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
