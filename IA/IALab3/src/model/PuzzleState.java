package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public class PuzzleState implements Cloneable, State, Comparable{

    public static final int[][] GOAL = new int[][]{
			 							{1,2,3},
			 							{4,0,5},
			 							{6,7,8}};
                                        //{1,2,3},
                                        //{4,5,6},
                                        //{7,8,0}};

	private int[][] currentState = new int[3][3];

    public PuzzleState getParent() {
        return parent;
    }

    public void setParent(PuzzleState parent) {
        this.parent = parent;
    }

    private PuzzleState parent = null;

    private float f;
    private float g;
    private float h;

    private Move move = null;

    public  Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public float getF() {
        return f;
    }

    public float getG() {
        return g;
    }

    public void setF(float f) {
        this.f = f;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getH() {

        return h;
    }

    private void randomFillCurrentMatrix(){
        int[] auxiliaryArray = new int[9];

        for (int i = 0; i< 9; i++){
            auxiliaryArray[i] = i;
        }

        shuffleArray(auxiliaryArray);
        int indexI = 0;
        int indexJ = 0;
        for (int  i = 0; i< 9; i++){
            if(0 == i%currentState.length && i!= 0){
                indexI++;
                indexJ = 0;
            }
            currentState[indexI][indexJ] = auxiliaryArray[i];
            indexJ++;
        }
    }

    private void shuffleArray(int[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        LinkedList<PuzzleState> treeRoad = new LinkedList<>();
        int numMoves = 0;
        PuzzleState currentNode = this;

        while (currentNode != null){
            treeRoad.addLast(currentNode);
            currentNode = currentNode.getParent();
            numMoves++;
        }

        while(false == treeRoad.isEmpty()){
            currentNode = treeRoad.removeLast();
            if(currentNode.getMove() != null){
                stringBuilder.append(currentNode.getMove().toString());
            }
            stringBuilder.append("\n");
            stringBuilder.append(this.printBoard(currentNode));
            stringBuilder.append("\n");
        }

        stringBuilder.append("Number of moves  :" + numMoves);
        return stringBuilder.toString();
    }

    public String printBoard(PuzzleState node){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i< node.getCurrentState().length; i++) {
            for(int j = 0; j< node.getCurrentState()[i].length; j++){
                stringBuilder.append(node.getCurrentState()[i][j] + " ");
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public PuzzleState(boolean initialize){
        this.setF(0);
        this.setG(0);
        this.setH(0);

        if (initialize == true) {
            //Initialize heuristic values to 0
            this.f = 0;
            this.g = 0;
            this.h = 0;
           // this.currentState = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
            randomFillCurrentMatrix();
        }
    }

    public int[] getBlankPosition(){
        int[] blankPosition = new int[2];

        for(int i=0; i< this.currentState.length; i++){
            for(int  j=0; j< this.currentState[i].length; j++){
                if(0 == this.currentState[i][j]){
                    blankPosition[0] = i;
                    blankPosition[1] = j;
                }
            }
        }
        return blankPosition;
    }

    public void setCurrentState(int[][] currentState) {
        this.currentState = currentState;
    }

    public int[][] getCurrentState() {
        return currentState;
    }

    private void moveBlank(Move move){
        int[] blankPosition = getBlankPosition();
        int aux;
        switch (move){
            case UP:

                aux = currentState[(blankPosition[0]-1)][blankPosition[1]];
                currentState[blankPosition[0]][blankPosition[1]] = aux;
                currentState[(blankPosition[0]-1)][blankPosition[1]] = 0;

                break;
            case DOWN:

                aux = currentState[(blankPosition[0]+1)][blankPosition[1]];
                currentState[blankPosition[0]][blankPosition[1]] = aux;
                currentState[(blankPosition[0]+1)][blankPosition[1]] = 0;

                break;
            case LEFT:

                aux = currentState[(blankPosition[0])][blankPosition[1]-1];
                currentState[blankPosition[0]][blankPosition[1]] = aux;
                currentState[(blankPosition[0])][blankPosition[1]-1] = 0;

                break;
            case RIGHT:

                aux = currentState[(blankPosition[0])][blankPosition[1]+1];
                currentState[blankPosition[0]][blankPosition[1]] = aux;
                currentState[(blankPosition[0])][blankPosition[1]+1] = 0;
                break;
        }
    }

    public ArrayList<PuzzleState> getStatesAfterMoves(){
        ArrayList<PuzzleState> newStates = new ArrayList<PuzzleState>();
        int[] blankLocation = this.getBlankPosition();

        for(Move move : Move.values()){
            switch (move){
                case UP:
                    if(blankLocation[0] > 0){ //can move up
                        PuzzleState clone = this.clone();
                        clone.setParent(this);
                        clone.setMove(Move.UP);
                        clone.moveBlank(Move.UP);
                        newStates.add(clone);
                    }
                    break;
                case DOWN:
                    if(blankLocation[0] < 2){ //can move down
                        PuzzleState clone = this.clone();
                        clone.setParent(this);
                        clone.setMove(Move.DOWN);
                        clone.moveBlank(Move.DOWN);
                        newStates.add(clone);
                    }
                    break;
                case LEFT:
                    if(blankLocation[1] > 0){ //can move left to [x][0 min]
                        PuzzleState clone = this.clone();
                        clone.setParent(this);
                        clone.setMove(Move.LEFT);
                        clone.moveBlank(Move.LEFT);
                        newStates.add(clone);
                    }
                    break;
                case RIGHT:

                    if(blankLocation[1] < this.currentState.length - 1){ // can move right
                        PuzzleState clone = this.clone();
                        clone.setParent(this);
                        clone.setMove(Move.RIGHT);
                        clone.moveBlank(Move.RIGHT);
                        newStates.add(clone);
                    }
                    break;
            }

        }
        return newStates;
    }

    public int[][] cloneMatrixState(){
        int[][] newMatrix = new int[this.currentState.length][this.currentState.length];
        for(int i = 0; i< this.currentState.length; i++) {
            for (int j = 0; j < this.currentState[i].length; j++) {
                newMatrix[i][j] = this.currentState[i][j];
            }
        }
        return newMatrix;
    }

    public PuzzleState clone(){
        PuzzleState cloned = new PuzzleState(false);
        cloned.setCurrentState(this.cloneMatrixState());

        return cloned;
    }

    @Override
    public int compareTo(Object o) {
        PuzzleState q = (PuzzleState)o;

        if(this.f > q.getF()){
            return 1;
        }else if(this.f < q.getF()){
            return -1;
        }
        return 0;
    }
}
