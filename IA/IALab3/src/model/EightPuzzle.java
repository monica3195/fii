package model;

import heuristics.Manhattan;
import heuristics.OutOfPlace;

public class EightPuzzle {
	
	public static void main(String[] args){

       /* HillClimbing hillClimbing = new HillClimbing(new Manhattan());
        PuzzleState solution = hillClimbing.hillClimbingSolve();

        System.out.println(solution.toString());*/

        AStar a = new AStar(new Manhattan());
        PuzzleState solution = a.aStarSolver();
        System.out.println(solution.toString());

        /*BestFirst bestFirst = new BestFirst(new Manhattan());
        PuzzleState solution = bestFirst.bestFirstSolve();

        System.out.println(solution.toString());
        */
	}

}
