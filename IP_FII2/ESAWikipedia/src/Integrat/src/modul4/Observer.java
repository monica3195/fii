package modul4;

public interface Observer {
	public void update(int fileCount);

	public void update(int fileCount, String filename);

	public boolean verifyTermination();
}
