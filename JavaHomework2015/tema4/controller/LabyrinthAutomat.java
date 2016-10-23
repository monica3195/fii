package tema4.controller;

import tema4.model.LabyrinthModel;
import tema4.view.LabyrinthView;

public class LabyrinthAutomat implements LabyrinthSolver{

	LabyrinthModel model;
	LabyrinthView view;
	
	public LabyrinthAutomat(LabyrinthModel model, LabyrinthView view) {

		this.model = model;
		this.view = view;
	}
	
}
