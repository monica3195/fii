package tema4.model;

public class ModelData {

	int[][] initMatrix;
	
	public int getRowCount() {
		if(initMatrix == null)
			throw new NullPointerException("Model is null");
		
		int rowCount=0;
		for (int[] is : initMatrix) {
			rowCount++;
		}
		return rowCount;
	}

	public int getClumnCount() {
		
		if(initMatrix == null)
			throw new NullPointerException("Model is null");
		
		int colCount=0;
		for (int is : initMatrix[0]) {
			colCount++;
		}
		return colCount;
	}

	public boolean isFreeAt(int indexX, int indexY) {
		if(initMatrix == null)
			throw new NullPointerException("Model is null");
		
		if(initMatrix[indexX][indexY] == 0)
			return true;
		else
			return false;
	}

	public boolean isWallAt(int indexX, int indexY) {
		if(initMatrix == null)
			throw new NullPointerException("Model is null");
		
		if(initMatrix[indexX][indexY] == 1)
			return true;
		else
			return false;
	}

	public int getStartCell() {
		if(initMatrix == null)
			throw new NullPointerException("Model is null");
		
		int rows = getRowCount();
		int cols = getClumnCount();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if(initMatrix[i][j] == -1)
					return i*cols + j;
			}
		}
		return -1;
	}

	public int getFinishCell() {
		if(initMatrix == null)
			throw new NullPointerException("Model is null");
		
		int rows = getRowCount();
		int cols = getClumnCount();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if(initMatrix[i][j] == -1)
					return i*cols + j;
			}
		}
		return 2;
	}

	public void setMatrix(int[][] readMatrixFromFile) {

		initMatrix = readMatrixFromFile;
	}

	public int[][] getMatrix() {
		return initMatrix;
	}

	public void asigMatrix(int[][] matrix) {
		this.initMatrix = matrix;
		
	}
	
}
