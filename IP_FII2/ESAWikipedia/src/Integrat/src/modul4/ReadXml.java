package modul4;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ReadXml extends Thread implements Observer {

	private String dump, index;
	private Observer observer;
	private boolean stop = false;
	private XMLMyHandler handler;
	private int lastFileToBeParsed;

	public ReadXml(String dump, String index, Observer tmpObserver, int nthFile) {
		lastFileToBeParsed = nthFile;
		observer = tmpObserver;
		this.dump = dump;
		this.index = index;
	}

	public void ReadData() {
		try {
			handler = new XMLMyHandler(index, this, lastFileToBeParsed);
			XMLReader s = XMLReaderFactory.createXMLReader();
			s.setContentHandler(handler);
			s.parse(dump);
		} catch (IOException ex) {
			Logger.getLogger(ReadXml.class.getName()).log(Level.SEVERE, null,
					ex);
			JOptionPane
					.showMessageDialog(new JPanel(),
							"Nu exista fisierul dump, sau nu are numele \"rodump.xml\"");
			System.exit(0);
		} catch (SAXException ex) {
			;
		}
	}

	public void run() {
		ReadData();
	}

	@Override
	public void update(int fileCount, String filename) {
		observer.update(fileCount, "Saved file:  " + filename);

	}

	@Override
	public void update(int fileCount) {
		if (fileCount == -1)
			stop = true;

	}

	@Override
	public boolean verifyTermination() {
		return stop;
	}

}
