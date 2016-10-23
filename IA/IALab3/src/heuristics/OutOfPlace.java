package heuristics;

/**
 * Created by uidj6605 on 10/27/2015.
 */
public class OutOfPlace implements Heuristic {
    private String heuristicName = new String("Out of place");

    @Override
    public int getFitness(int[][] goalMatrix, int[][] currentMatrix) {


            int outOfPlace = 0;
            for (int i = 0; i < currentMatrix.length; i++){
                for(int j = 0; j < currentMatrix[i].length; j++){
                    if (goalMatrix[i][j] != currentMatrix[i][j]){
                        outOfPlace++;
                    }
                }
            }
        return  outOfPlace;
    }

    @Override
    public String getHeuristicName() {
        return this.heuristicName;
    }
}
