package vsm;

import vsm.events.Dispatcher;
import vsm.events.ProgressDispatcher;

public class Main {

	public static void main(String[] args) {
		
		// Create a VSM instance with input and output paths.
		Vsm vsm = new Vsm("D:\\ip\\sample-input", "D:\\ip\\output");
		
		// Set the event dispatcher (optional).
		Dispatcher events = new ProgressDispatcher();
		vsm.setDispatcher(events);
		
		// Start processing.
		vsm.start();
	}

}
