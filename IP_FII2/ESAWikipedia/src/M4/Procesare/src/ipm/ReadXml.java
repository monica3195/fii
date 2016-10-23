/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ReadXml {
	
	private String dump, index;
	
	public ReadXml(String dump, String index) {
		this.dump = dump;
		this.index = index;
	}
    
    public void ReadData() {
        
        try {
           XMLReader s = XMLReaderFactory.createXMLReader();
           s.setContentHandler(new XMLMyHandler(index));
           s.parse(dump);
        } catch (SAXException | IOException ex) {
        	Logger.getLogger(ReadXml.class.getName()).log(Level.SEVERE, null, ex);
        	JOptionPane.showMessageDialog(new JPanel(), "Nu exista fisierul dump, sau nu are numele \"rodump.xml\"");
            System.exit(0);
        }        
    }
}
