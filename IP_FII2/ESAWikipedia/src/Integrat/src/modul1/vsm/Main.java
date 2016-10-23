package modul1.vsm;

import modul1.vsm.events.Dispatcher;
import modul1.vsm.events.ProgressDispatcher;

public class Main {

	public static void main(String[] args) {
		
		// Create a VSM instance with input and output paths.
		Vsm vsm = new Vsm("outputM3", "outputM1");
		
		// Set the event dispatcher (optional).
		Dispatcher events = new ProgressDispatcher();
		vsm.setDispatcher(events);
		
		// Start processing.
		vsm.start();
	}

}
