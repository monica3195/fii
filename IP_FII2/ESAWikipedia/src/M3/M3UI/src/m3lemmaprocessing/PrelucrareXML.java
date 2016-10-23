package m3lemmaprocessing;

import java.io.File;
import java.io.IOException;
import javax.swing.JProgressBar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author wiki
 */

public class PrelucrareXML {
	JProgressBar bar;
	public PrelucrareXML(JProgressBar b){
		bar=b;
	}
	public void run() {
		try {
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XMLHandler handler;
			File input;
			int c = 0;
			String sep = System.getProperty("file.separator");
			input = new File(System.getProperty("user.dir") + sep + "M3"+sep+"automaticLemmatizationOutput");
			if (input.exists() && input.isDirectory()) {
				File[] dirs = input.listFiles();
				bar.setMaximum(dirs.length);
				for (File dir : dirs) {//for each directory
					bar.setValue(c++);
					if (dir.isDirectory()) {
						File[] fisiere = dir.listFiles();
						for (File fisier : fisiere) {//for each file in the directory
							handler = new XMLHandler(fisier.getParentFile(),fisier.getName());
							try{
								saxParser.parse(fisier, handler);
							}
							catch(SAXException | IOException e){
								System.out.println(" Eroare. Fisier gol sau format gresit!");
							}
						}
					}
					else {
						handler = new XMLHandler(dir.getParentFile(),dir.getName());
						saxParser.parse(dir, handler);
					}
				}
			} 
			else {
				System.out.println("Folderul input nu exista!");
			}
		}
		catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}
