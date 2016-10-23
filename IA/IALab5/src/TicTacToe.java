import java.util.ArrayList;
import java.util.Random;

/**
 * Created by uidj6605 on 11/10/2015.
 */
public class TicTacToe {

    public static void main(String[] args){
        Matrix gameMatrix = new Matrix();
        Random randomSeed  = new Random();

        System.out.println(gameMatrix);

        System.out.println("Select  turn : \n 1 - AI \n 2 - User \n");

        int whoIsMoving = gameMatrix.stdinScanner.nextInt();

        if(whoIsMoving == 1){
            // computer first move is random
            Point firstRandomPoint = new Point(randomSeed.nextInt(gameMatrix.getMLength()), randomSeed.nextInt(gameMatrix.getMLength()));
            gameMatrix.placeMove(firstRandomPoint, Player.AIPlayer);
            System.out.println(gameMatrix);
        }

        while (false == gameMatrix.gameOver()){

            System.out.println("User move : ");
            Point movePoint = new Point(gameMatrix.stdinScanner.nextInt(), gameMatrix.stdinScanner.nextInt());

            if(! gameMatrix.isPointEmpty(movePoint)){
                System.out.println("Invalid move !");
                continue;
            }

            gameMatrix.placeMove(movePoint, Player.HUMANPlayer);

            System.out.println(gameMatrix);

            if ( true == gameMatrix.gameOver()){
                    break;
            }

            gameMatrix.firstLevelNodes = new ArrayList<>();
            gameMatrix.minMax(0, Player.AIPlayer.getEnumValue());

            for(PointAndScore pointAndScore : gameMatrix.firstLevelNodes){
                System.out.println("Point : " + pointAndScore.point + " score : " + pointAndScore.score);
            }
            gameMatrix.placeMove(gameMatrix.returnBestMove(), Player.AIPlayer);
            System.out.println(gameMatrix);
        }

        if(true == gameMatrix.checkWon(Player.AIPlayer)){
            System.out.println("You lost");
        }else if (true == gameMatrix.checkWon(Player.HUMANPlayer)){
            System.out.println("You win !,  No way"); // No way
        }else{
            System.out.println("Nobody won");
        }

    }
}
