package modul1.vsm.gui;

import modul1.vsm.Normalizer;
import modul1.vsm.Vsm;
import modul1.vsm.events.Dispatcher;
import modul1.vsm.gui.ProgressDispatcher;

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
