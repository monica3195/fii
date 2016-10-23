import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by uidj6605 on 11/10/2015.
 */
public class Matrix {

    private int[][] matrix;

    final int DEFAULT_MATRIX_LENGTH = 3;
    int howManyToWin;
    public Point AIMove;

    String wonReason = new String();
    Scanner stdinScanner = new Scanner(System.in);

    public List<PointAndScore> firstLevelNodes = new ArrayList<>();

    public Matrix(){
        this.matrix = new int[DEFAULT_MATRIX_LENGTH][DEFAULT_MATRIX_LENGTH];
        this.howManyToWin = DEFAULT_MATRIX_LENGTH;
    }

    public Matrix(int length){
        this.matrix = new int[length][length];
        this.howManyToWin = length;
    }

    public Matrix(int nLength, int mLength){
        this.matrix = new int[nLength][mLength];
        this.howManyToWin = Math.min(nLength, mLength);
    }

    public Matrix(int nLength, int mLength, int howManyToWin){
        this.matrix = new int[nLength][mLength];
        if(howManyToWin <= Math.min(nLength, mLength)){
            this.howManyToWin = howManyToWin;
        }else{
            this.howManyToWin = Math.min(nLength, mLength);
        }
    }

    public boolean gameOver(){
        return (this.checkWon(Player.HUMANPlayer) || this.checkWon(Player.AIPlayer) || getAvailablePoints().isEmpty());
    }

    public List<Point> getAvailablePoints(){
        List<Point> availablePoints = new ArrayList<>();

        for(int i = 0; i < this.matrix.length; i++){
            for(int j = 0; j < this.matrix[i].length; j++){
                if(0 == this.matrix[i][j]){
                    availablePoints.add(new Point(i, j));
                }
            }
        }

        return availablePoints;
    }

    public void placeMove(Point point, Player player){
        this.matrix[point.x][point.y] = player.getEnumValue();
    }

    public String toString2DIntArray(int[][] array){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < array.length; i++){
            stringBuilder.append("[ ");
            for(int j = 0; j < array[i].length; j++){
                if(array[i][j] == Player.HUMANPlayer.getEnumValue()){
                    stringBuilder.append("X");
                }else if (array[i][j] == Player.AIPlayer.getEnumValue()){
                    stringBuilder.append("O");
                }else{
                    stringBuilder.append("-");
                }

                if(j < array[i].length - 1) {
                    stringBuilder.append(" ");
                }
            }

            stringBuilder.append(" ]\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        this.getAvailablePoints();
       /* return "Matrix{" +
                //"available moves=" + availablePoints +
                ",\nmatrix= \n" + this.toString2DIntArray(this.matrix) +
                ",N = "  + this.matrix.length +
                "\n,M = " +  this.matrix[0].length +
                '}';*/
        return this.toString2DIntArray(this.matrix);
    }

    public int minMax(int recursionLevel, int turn){

        if(this.checkWon(Player.AIPlayer)){
            return +1;
        }
        if(this.checkWon(Player.HUMANPlayer)){
            return -1;
        }

        List<Point> availablePoints = this.getAvailablePoints();
        if(availablePoints.isEmpty()){
            return 0;
        }

        List<Integer> scores = new ArrayList<>();

        for(int i = 0; i < availablePoints.size(); i++){
            Point currentAvailablePoint = availablePoints.get(i);

            if(turn == Player.AIPlayer.getEnumValue()){

                this.placeMove(currentAvailablePoint, Player.AIPlayer);
                int currentScore = minMax(recursionLevel+1, Player.HUMANPlayer.getEnumValue());
                scores.add(currentScore);

                if((recursionLevel == 0)){
                    this.firstLevelNodes.add(new PointAndScore(currentScore, currentAvailablePoint));
                }
            }else if(turn == Player.HUMANPlayer.getEnumValue()){
                this.placeMove(currentAvailablePoint, Player.HUMANPlayer);
                scores.add(minMax(recursionLevel+1, Player.AIPlayer.getEnumValue()));
            }
            this.matrix[currentAvailablePoint.x][currentAvailablePoint.y] = 0;
        }

       if(turn ==  Player.AIPlayer.getEnumValue()){
            return returnMax(scores);
        }
        else{
            return returnMin(scores);
        }
    }

    public int returnMax(List<Integer> list){
        int max = Integer.MIN_VALUE;
        int maxIndex = -1;
        for(int i = 0 ; i< list.size(); i++){
            if(list.get(i) > max){
                max = list.get(i);
                maxIndex = i;
            }
        }
        return list.get(maxIndex);
    }

    public int returnMin(List<Integer> list){
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int i = 0 ; i< list.size(); i++){
            if(list.get(i) < min){
                min = list.get(i);
                minIndex = i;
            }
        }
        return list.get(minIndex);
    }

    public boolean checkWon(Player currentPlayer) {

        int sameValuesCounter;

        // check win combinations in lines
        for(int i = 0; i < this.matrix.length; i++) {
            for (int j = 0 ; j <= this.matrix[i].length - howManyToWin; j++){
                sameValuesCounter = 0;
                //try to find howManyToWin continuous
                for(int k = 0; k < howManyToWin; k++){
                    //check in line
                    if( this.matrix[i][j+k] == currentPlayer.getEnumValue()) {
                        sameValuesCounter++;
                    }else {
                        sameValuesCounter = 0;
                    }
                }
                if(howManyToWin == sameValuesCounter){
                    return true;
                }
            }
        }
        //check win combinations in columns
        for(int j = 0; j< this.matrix[0].length; j++ ){
            for(int i = 0; i<= this.matrix.length - howManyToWin; i++){
                sameValuesCounter = 0;
                for(int k = 0 ; k< howManyToWin; k++){
                    if(this.matrix[i+k][j] == currentPlayer.getEnumValue()){
                        sameValuesCounter++;
                    }else {
                        sameValuesCounter = 0;
                    }
                }
                if(howManyToWin == sameValuesCounter){
                    return true;
                }
            }
        }

        //check win combination on first diagonals
        for(int i = 0; i<= this.matrix.length - howManyToWin; i++){
            for(int j  = 0; j<= matrix[i].length - howManyToWin; j++){
                sameValuesCounter = 0;
                for(int k = 0; k < howManyToWin; k++){
                    try {
                        if (this.matrix[i + k][j + k] == currentPlayer.getEnumValue()) {
                            sameValuesCounter++;
                        } else {
                            sameValuesCounter = 0;
                        }
                    }catch (ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
                if(howManyToWin == sameValuesCounter){
                    return true;
                }
            }
        }

        // check win combinations on second diagonal
        for(int i = 0;  i <= this.matrix.length - howManyToWin; i++ ){
            for(int j = this.matrix[i].length - 1; j >= howManyToWin - 1; j--  ){
                sameValuesCounter = 0;
                for(int k = 0; k < howManyToWin; k++){
                    try{
                        if(this.matrix[i+k][j-k] == currentPlayer.getEnumValue()){
                            sameValuesCounter++;
                        }else {
                            sameValuesCounter = 0;
                        }
                    }catch (ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
                if(sameValuesCounter == howManyToWin){
                    this.wonReason = "Diagonal right-> left from [ " + i + "," + j + "]";
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPointEmpty(int x, int y){
        try{
            return (this.matrix[x][y] == 0)?true:false;
        }catch (ArrayIndexOutOfBoundsException  e){
            return false;
        }
    }

    public boolean isPointEmpty(Point point){
        try{
            return (this.matrix[point.x][point.y] == 0)?true:false;
        }catch (ArrayIndexOutOfBoundsException  e){
            return false;
        }
    }

    public Point returnBestMove() {

        int MAX = Integer.MIN_VALUE;
        int bestMoveIndex = -1;
        for(int i = 0; i < this.firstLevelNodes.size(); i++){
            if(MAX < firstLevelNodes.get(i).score){
                MAX = firstLevelNodes.get(i).score;
                bestMoveIndex = i;
            }
        }
        return this.firstLevelNodes.get(bestMoveIndex).point;
    }

    public int getMLength(){
        return this.matrix[0].length;
    }

    public int getNLength(){
        return this.matrix.length;
    }
}
