package tema4.model;

import java.util.ArrayList;

import tema4.view.LabyrinthView;

public interface LabyrinthModel {

	ModelData modelData = new ModelData();
	ArrayList<LabyrinthView> observers =  new ArrayList<LabyrinthView>();
	
	
	ModelData getState();//ret matricea currenta
	void setState(int[][] matrix);
	void attach(LabyrinthView view);
	void dettach(LabyrinthView view);
	void notifyAllObservers();
	
	int getRowCount();
	int getClumnCount();
	boolean isFreeAt(int indexX, int indexY);
	boolean isWallAt(int indexX, int indexY);
	int getStartCell();
	int getFinishCell();
}
