package dqgui;

import java.awt.Color;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class DqHighlightingTextArea {
	JTextArea ta = new JTextArea();
	Highlighter highlighter = ta.getHighlighter();
	HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);
	HashSet<String> tagsSet = new HashSet<String>();
	CustomDocumentListener listener = new CustomDocumentListener();

	static String patternStr = "#[\\w|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+\\b";

	public DqHighlightingTextArea() {
		ta.getDocument().addDocumentListener(listener);
		this.init();
	}
	
	public void init() {
		tagsSet.clear();
		highlighter.removeAllHighlights();
	}

	public void highlighting() {
		int startIndex = 0, endIndex = 0;
		init();
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(ta.getText());
		while (true) {
			if (matcher.find(endIndex)) {
				startIndex = matcher.start();
				endIndex = matcher.end();
				tagsSet.add(matcher.group());
				try {
					highlighter.addHighlight(startIndex, endIndex, painter);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return;
			}

		}
	}

	public JTextArea getTextArea() {
		return this.ta;
	}

	public HashSet<String> getTags() {
		return this.tagsSet;
	}

	class CustomDocumentListener implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			highlighting();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			highlighting();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			highlighting();

		}

	}
}
