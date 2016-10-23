package modul4;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLMyHandler extends DefaultHandler {

	private Observer observer;
	private boolean tag = false, id = false, firstID = true;
	private SaveFile save;
	private DumpFile file = new DumpFile("  ");
	private StringBuilder content = new StringBuilder();
	private int fileCount = 0;
	private int lastFileToBeParsed = 0;
	private Redirect redir;

	public XMLMyHandler(String index, Observer tmpObserver, int nthFile) {
		super();
		lastFileToBeParsed = nthFile;
		observer = tmpObserver;
		save = new SaveFile(index);
		redir = new Redirect(index);
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
			// System.out.println("Start element: " + localName);
		}

		if (localName.equals("id") && firstID == true) {
			id = true;
			// System.out.println("Start element: " + localName);
			firstID = false;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (localName.equals("id") || localName.equals("text")) {
			// System.out.println("End element: " + localName);
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
				if (content.toString().startsWith("#REDIRECT"))
					redir.setRedirect(content.toString(), file.getName());
				file.setContent(new Cleaner().clean(content.toString())
						.replaceAll("\\{\\{[^}]*\\}\\}", ""));
			} catch (Exception e) {
				System.out.println("Error while cleaning.");
			}

			// int okfiles = 1312, index = 0;
			// if (index >= okfiles)
			fileCount++;
			if (fileCount >= lastFileToBeParsed) {
				System.out.println(fileCount);
				observer.update(fileCount, file.getName());
				save.addFile(file);
				save.saveFile();
				// } else {
				// index++;
				// }
			}
			content.delete(0, content.length());

		}
	}

	// To take specific actions for each chunk of character data (such as
	// adding the data to a node or buffer, or printing it to a file).
	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		if (id == true) {
			// System.out.println("ID: " + new String(ch, start, length));
			file.setName(new String(ch, start, length));
			id = false;
		}

		if (tag == true) {
			// System.out.print(new String(ch, start, length));
			content.append(new String(ch, start, length));
		}

		try {
			isStopped(observer.verifyTermination());
		} catch (TerminationException e) {
			throw new SAXException();
		}
	}

	public void isStopped(boolean stop) throws TerminationException {
		if (stop)
			throw new TerminationException();
	}
}
