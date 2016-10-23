package m3lemmaprocessing;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author wiki
 */

public class PrelucrareXML {

	public static void main(String args[]) {
		try {
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XMLHandler handler;
			File input;
			
			if (args.length > 0) {
				input = new File(args[0]);
			} else {
				input = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "input");
			}
			if (input.exists() && input.isDirectory()) {
				File[] dirs = input.listFiles();
				for (File dir : dirs) {//for each directory
					if (dir.isDirectory()) {
						File[] fisiere = dir.listFiles();
						for (File fisier : fisiere) {//for each file in the directory
							handler = new XMLHandler(fisier.getParentFile(),fisier.getName());
							try{
								saxParser.parse(fisier, handler);
							}
							catch(Exception e){
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
