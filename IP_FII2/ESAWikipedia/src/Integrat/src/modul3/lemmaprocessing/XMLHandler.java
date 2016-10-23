package modul3.lemmaprocessing;

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
		if(f.getName().equals("automaticLemmatizationOutput"))
			output = new File(System.getProperty("user.dir") + sep + "lemmaProcessingOutput");
		else
			output = new File(System.getProperty("user.dir") + sep + "lemmaProcessingOutput" + sep + f.getName());
		if (!output.exists()) {
			output.mkdirs();
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
	}

	@Override
	public void endDocument() {
		try {
			outFile.write(linie.toString());
			outFile.close();
		} catch (IOException ex) {
			Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		int length = attributes.getLength();
		for (int i = 0; i < length; i++) {
			String name = attributes.getQName(i);
			if (name.equals("LEMMA")) {
				linie.append(attributes.getValue(i)).append(" ");
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
