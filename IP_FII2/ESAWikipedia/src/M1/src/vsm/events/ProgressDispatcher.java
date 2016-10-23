package vsm.events;

import java.io.File;

public class ProgressDispatcher implements Dispatcher {
	
	public ProgressDispatcher() {
		
	}

	@Override
	public void startProcessing(int numberOfDirs) {
		// TODO Auto-generated method stub
	}

	@Override
	public void startDistances() {
		// TODO Auto-generated method stub
	}

	@Override
	public void processingDir(File dir) {
		// TODO Auto-generated method stub
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
	}

	@Override
	public void error(String message, Exception e) {
		System.out.println(message);
		
		e.printStackTrace();
	}
	
}
