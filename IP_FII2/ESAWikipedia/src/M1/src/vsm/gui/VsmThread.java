package vsm.gui;

import vsm.Normalizer;
import vsm.Vsm;
import vsm.events.Dispatcher;
import vsm.gui.ProgressDispatcher;

public class VsmThread implements Runnable {

	private Vsm vsm;

	public VsmThread(MainWindow window, String input, String output, int normalizeRate) {
		Dispatcher events = new ProgressDispatcher(window);
		
		vsm = new Vsm(input, output);
		
		if (normalizeRate != 0) {
			vsm.setNormalizer(new Normalizer(normalizeRate));
		}
		
		vsm.setDispatcher(events);
	}

	@Override
	public void run() {
		vsm.start();
	}

}
