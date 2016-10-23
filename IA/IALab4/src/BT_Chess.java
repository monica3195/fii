import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dimitrie on 01/11/2015.
 */
public class BT_Chess {
    static private int counter = 0;
    static private final int QUEEN = 1;
    static private final int NOTHING = 0;
    static private final int NO_QUEEN = -1;

    static private int[][] chessBoard;
    static private List<int[][]> solutions;
    static private int queensToPlace;

    static public void init(int boardSize){
        queensToPlace = boardSize;
        chessBoard = new int[boardSize][boardSize];
        solutions = new ArrayList<>();

        for(int i = 0; i< chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                chessBoard[i][j] = 0;
            }
        }
    }

    static private String printBoard(int[][] board){
        StringBuilder boardOutput = new StringBuilder();

        for(int i=0; i< board.length; i++){
            for(int j=0; j< board[i].length; j++){

                if(board[i][j] == 1){
                    boardOutput.append("Q ");
                }else{
                    boardOutput.append("N ");
                }
            }
            boardOutput.append('\n');
        }

        return boardOutput.toString();
    }

    static public void btk(int queensPlaced, int i){

        if(queensToPlace == queensPlaced){
            counter++;
            System.out.println(printBoard(chessBoard));
        }else{
            //for(int i = 0; i< chessBoard.length; i++){
                for(int j = 0; j< chessBoard.length; j++){

                    if(true == isSafe(i, j)) {
                        chessBoard[i][j] = QUEEN;
                        btk(queensPlaced+1,i+1);
                        chessBoard[i][j] = NOTHING;
                    }
                }
           // }
        }
    }

    static private boolean isSafe(int i, int j){
        for(int k = 0; k < chessBoard.length; k++){
            if(chessBoard[k][j] == QUEEN){
                return false;
            }

            if(chessBoard[i][k] == QUEEN){
                return false;
            }
        }

        for(int k=0; k< chessBoard.length; k++){
            for(int l=0; l< chessBoard.length; l++){
                if(chessBoard[k][l] == QUEEN){
                    if(Math.abs(k-i) == Math.abs(j-l)){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    static public void solve(){
        btk(0, 0);
        System.out.println("Counter : " + counter);
    }
}
