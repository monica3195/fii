/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul3.main;

import javax.swing.*;
import modul3.automaticlemmatization.M3AutomaticLemmatization;
import modul3.lemmaprocessing.PrelucrareXML;
import modul3.resultsbinding.M3ResultsBinding;
import modul3.simpleconceptstatistics.SimpleConceptStatistics;
/**
 *
 * @author Ovidiu
 */
public class backgroundThread implements Runnable{
	JProgressBar bar;
	JButton start;
	JLabel status;
	public backgroundThread(JProgressBar p, JButton b, JLabel s){
		bar = p;
		start = b;
		status = s;
	}
	@Override
	public void run() {
		start.setEnabled(false);
		
		status.setText("Pas 1/4  Lematizez dump-ul...");
		bar.setStringPainted(true);
		M3AutomaticLemmatization autoLemma = new M3AutomaticLemmatization(bar);
		System.out.println("Lematizez...");
		autoLemma.run();
		
		status.setText("Gata Lematizarea");
		bar.setValue(0);
		status.setText("Pas 2/4  Procesez XML-urile...");
		PrelucrareXML lemmaProcessing = new PrelucrareXML(bar);
		System.out.println("Procesez XML...");
		lemmaProcessing.run();
		
		status.setText("Gata procesarea");
		bar.setValue(0);
		SimpleConceptStatistics conceptIdentifier = new SimpleConceptStatistics(bar,status);
		System.out.println("Identific concepte...");
		conceptIdentifier.run();
		
		status.setText("Gata identificarea");
		bar.setValue(0);
		status.setText("Pas 4/4 Imbin rezultatele...");
		M3ResultsBinding rezBind = new M3ResultsBinding(bar);
		System.out.println("Combin rezultatele...");
		rezBind.run();
		
		status.setText("Am terminat.  :)");
		bar.setValue(bar.getMaximum());
		start.setEnabled(true);
		System.out.println("Gata!");
	}
}
