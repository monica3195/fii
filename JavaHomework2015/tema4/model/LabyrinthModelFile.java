package tema4.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import tema4.view.LabyrinthView;

public class LabyrinthModelFile implements LabyrinthModel, ParseFile{

	@Override
	public void readMatrix(String file) throws Exception {
		File ffile = new File(file);
		modelData.setMatrix(readMatrixFromFile(ffile));
	}

	private int[][] readMatrixFromFile(File ffile) throws Exception,
			FileNotFoundException {
		if(!ffile.exists())
			throw new Exception("Fiserul nu exista");
		
		Scanner input = null;
		try {
			input = new Scanner (ffile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ArrayList<String> rowsS = new ArrayList<String>();
		
		while(input.hasNextLine())
		{
			rowsS.add(input.nextLine());
		}
		
		int rows = rowsS.size();
		int columns = (rowsS.get(0).split("\\s+")).length;
		
		int[][] matrix = new int[rows][columns];
		
		input.close();
		
		for(int i = 0; i < rows; ++i)
		{
			String line = rowsS.get(i);
			int [] inline = new int[rows];
			String[] inlineString  = line.split("\\s+");
			int j=0;
			for (String string : inlineString) {
				inline[j] = Integer.parseInt(string);
				j++;
				}
		matrix[i] = inline;	
		}	
		return matrix;
	}

	@Override
	public int getRowCount() {

		return modelData.getRowCount();
	}

	@Override
	public int getClumnCount() {
		
		return modelData.getClumnCount();
	}

	@Override
	public boolean isFreeAt(int indexX, int indexY) {

		return modelData.isFreeAt( indexX,  indexY);
	}

	@Override
	public boolean isWallAt(int indexX, int indexY) {

		return modelData.isWallAt( indexX,  indexY);
	}

	@Override
	public int getStartCell() {

		return modelData.getStartCell();
	}

	@Override
	public int getFinishCell() {

		return modelData.getFinishCell();
	}

	@Override
	public ModelData getState() {
		return modelData;
	}

	@Override
	public void setState(int[][] matrix) {
		this.modelData.asigMatrix(matrix);
		notifyAll();
	}

	@Override
	public void attach(LabyrinthView view) {
		observers.add(view);
	}
	
	@Override
	public void dettach(LabyrinthView view) {

		observers.remove(view);
		
	}

	@Override
	public void notifyAllObservers() {

		for (LabyrinthView labyrinthView : observers) {
			labyrinthView.update();
		}
	}

}
