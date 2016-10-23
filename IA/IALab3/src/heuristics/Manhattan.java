package heuristics;

public class Manhattan implements Heuristic{

	private String heuristicName = new String("Manhattan Distance");
	@Override
	 public int getFitness(int[][] goalMatrix, int[][] currentMatrix) {
		int fitness = 0;
		for(int  i=0; i< goalMatrix.length; i++){
			for(int j=0; j< goalMatrix[i].length; j++){
				int coords[] = getCoordinatesForValue(currentMatrix[i][j], goalMatrix);

				if ((coords[0] != -1)&& (coords[1] != -1)) {
					fitness += (Math.abs(i - coords[0]) + Math.abs(j - coords[1]));
				}
			}
		}
		return fitness;
	}

	@Override
	public String getHeuristicName() {
		return this.heuristicName;
	}

	private int[] getCoordinatesForValue(int value, int[][] matrix){
		int[] coords = new int[]{-1,-1};

		for(int i=0; i< matrix.length; i++){
			for(int j = 0; j< matrix[i].length; j++)
			{
				if(value == matrix[i][j]){
					coords[0] = i;
					coords[1] = j;
					break;
				}
			}
		}
		return coords;
	}
}
