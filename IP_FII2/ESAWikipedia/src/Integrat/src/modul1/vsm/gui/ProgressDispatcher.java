package modul1.vsm.gui;

import java.io.File;

import modul1.vsm.events.Dispatcher;
import modul1.vsm.gui.MainWindow;

public class ProgressDispatcher implements Dispatcher {
	
	private MainWindow window;

	public ProgressDispatcher(MainWindow window) {
		this.window = window;
	}

	@Override
	public void startProcessing(int numberOfDirs) {
		window.getDisplay().asyncExec(new Runnable() {
			public void run() {
				window.start(numberOfDirs);
			}
		});
	}

	@Override
	public void startDistances() {
		window.getDisplay().asyncExec(new Runnable() {
			public void run() {
				window.changeLabel("Calculating concept distances: ");
			}
		});
	}

	@Override
	public void processingDir(File dir) {
		window.getDisplay().asyncExec(new Runnable() {
			public void run() {
				window.update(dir.getName());
			}
		});
	}

	@Override
	public void finish() {
		window.getDisplay().asyncExec(new Runnable() {
			public void run() {
				window.finish();
			}
		});
	}

	@Override
	public void error(String message, Exception e) {
		System.out.println(message);
		
		e.printStackTrace();
	}
	
}
