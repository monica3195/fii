/**
 * Created by Dimitrie on 29/10/2015.
 */
public class BT_Queen {

    public static void printBoard(int[] board){
        for(int i = 0; i< board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i] == j){
                    System.out.print(" Q ");
                }else{
                    System.out.print(" N ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isOk(int[] board, int n){
        for(int i = 0; i< n; i++){
            if(board[i] == board[n]){
                return false;
            } // same column
            if((board[i] - board[n]) == (n-i)){
                return false;
            }
            //same diagonal
            if((board[n] - board[i]) == (n-i)){
                return false;
            }
            //Other diagonal
        }
        return true;
    }

    public static void solve(int[] board, int n){
        int N = board.length;

        if(n == N){
            printBoard(board);
        }else{
            for(int i = 0; i< N; i++){
                board[n] = i;
                if(isOk(board, n)){
                    solve(board, n+1);
                }
            }
        }
    }

    public static void QueensInit(int boardSize){
        int[] board = new int[boardSize];
        solve(board, 0);
    }
    public static void main(String args[]){
        QueensInit(4);
    }
}
