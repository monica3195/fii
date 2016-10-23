package modul2.gui;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import modul2.ProcessingWikipedia;

public class BackgroundRun implements Runnable {
	private JProgressBar pbar;
	private JTextArea information;
	private JButton start;
	private ProcessingWikipedia processingWikipedia;

	public BackgroundRun(JProgressBar pbar, JTextArea information, JButton start) {
		this.pbar = pbar;
		this.information = information;
		this.start = start;

	}

	public void run() {

		pbar.setValue(0);
		pbar.setStringPainted(true);

		processingWikipedia = new ProcessingWikipedia(pbar, information);
		processingWikipedia.processingFileWikipedia("DumpWikiRO");
		if (information.getText().contains("Finnish"))
			start.setText("Close");
	}
}
