/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipm;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLMyHandler extends DefaultHandler {

	private boolean tag = false, id = false, firstID = true;
	private SaveFile save;
	private DumpFile file = new DumpFile("  ");
	private StringBuilder content = new StringBuilder();

	public XMLMyHandler(String index) {
		super();
		save = new SaveFile(index);
	}

	@Override
	public void startDocument() {
		System.out.println("begin parsing...");
	}

	@Override
	public void endDocument() {
		System.out.println("end parsing...");
	}

	public void startElement(String nameSpaceURI, String localName,
			String qName, Attributes atts) {
		if (localName.equals("text")) {
			tag = true;
			firstID = true;
			System.out.println("Start element: " + localName);
		}

		if (localName.equals("id") && firstID == true) {
			id = true;
			System.out.println("Start element: " + localName);
			firstID = false;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (localName.equals("id") || localName.equals("text")) {
			System.out.println("End element: " + localName);
		}

		if (localName.equals("text")) {
			int x;// ,y;
			tag = false;
			x = content.indexOf("==");
			// y=content.indexOf("== Note ==");
			if (x >= 0) {// ||y>=0){
				// if(y<0)
				content.replace(x, content.length(), "");
				// else
				// content.replace(y, content.length(), "");
			}

			try {
				file.setContent(new Cleaner().clean(content.toString())
						.replaceAll("\\{\\{[^}]*\\}\\}", ""));
			} catch (Exception e) {
				System.out.println("Error while cleaning.");
			}

//			int okfiles = 1312, index = 0;
//			if (index >= okfiles) {
				save.addFile(file);
				save.saveFile();
//			} else {
//				index++;
//			}
			content.delete(0, content.length());

		}
	}

	// To take specific actions for each chunk of character data (such as
	// adding the data to a node or buffer, or printing it to a file).
	@Override
	public void characters(char ch[], int start, int length) {
		if (id == true) {
			System.out.println("ID: " + new String(ch, start, length));
			file.setName(new String(ch, start, length));
			id = false;
		}

		if (tag == true) {
			System.out.print(new String(ch, start, length));
			content.append(new String(ch, start, length));
		}
	}
}
