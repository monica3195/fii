package modul5.esaWikipedia;

public class Line {
	private int startx;
	private int starty;
	private int endx;
	private int endy;
	
	public Line (int startx, int starty, int endx, int endy) {
		this.startx = startx;
		this.starty = starty;
		this.endx = endx;
		this.endy = endy;
	}
	
	public int getStartX() {
		return startx;
	}
	
	public int getStartY() {
		return starty;
	}
	
	public int getEndX() {
		return endx;
	}
	
	public int getEndY() {
		return endy;
	}
}
