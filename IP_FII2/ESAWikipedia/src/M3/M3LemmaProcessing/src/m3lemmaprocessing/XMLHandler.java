package m3lemmaprocessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author wiki
 */
public class XMLHandler extends DefaultHandler {

	private StringBuffer linie = new StringBuffer();
	File output;
	String sep = System.getProperty("file.separator");
	FileWriter outFile;

	public XMLHandler(File f, String nume) {
		super();
		nume=nume.replace(".xml", "");
		if(f.getName().equals("input"))
			output = new File(System.getProperty("user.dir") + sep + "output");
		else
			output = new File(System.getProperty("user.dir") + sep + "output" + sep + f.getName());
		if (!output.exists()) {
			output.mkdir();
			System.out.println("Am construit in output folderul: " + output.getName());
		}
		output = new File(output.getAbsolutePath() + sep + nume);
	}

	@Override
	public void startDocument() {
		try {
			outFile = new FileWriter(output);
		} catch (UnsupportedEncodingException | FileNotFoundException ex) {
			Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
//		System.out.print("begin parsing file "+output.getName()+".xml... ");
	}

	@Override
	public void endDocument() {
		try {
			outFile.write(linie.toString());
			outFile.close();
		} catch (IOException ex) {
			Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
//		System.out.println(" Success!");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		int length = attributes.getLength();
		for (int i = 0; i < length; i++) {
			String name = attributes.getQName(i);
			//System.out.println("Name:" + name);   //pt a afla cuvantul lema
			if (name.equals("LEMMA")) {
				linie.append(attributes.getValue(i)+" ");
				//System.out.println("Value:" + value);   //valoarea lemei
				break;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("S")) {
			linie.append("\n");
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

	}
}