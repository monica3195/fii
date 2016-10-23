package vsm.events;

import java.io.File;

public interface Dispatcher {
	
	public void startProcessing(int numberOfDirs);
	
	public void startDistances();
	
	public void processingDir(File dir);
	
	public void finish();
	
	public void error(String message, Exception e);
}
