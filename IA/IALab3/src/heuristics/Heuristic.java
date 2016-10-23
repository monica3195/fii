package heuristics;

public interface Heuristic {

	String heuristicName = new String("Heuristic Interface");
	
	int getFitness(int[][] goalMatrix, int[][] currentMatrix);

	String getHeuristicName();
}
