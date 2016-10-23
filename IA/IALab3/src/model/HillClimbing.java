package model;

import heuristics.Heuristic;

import java.util.ArrayList;

/**
 * Created by uidj6605 on 10/27/2015.
 */
public class HillClimbing implements ProblemSolver{

    private PuzzleState firstState;
    private PuzzleState currentState;


    private Heuristic heuristic;
    boolean finish = false;
    boolean solutionFound = false;
    int globalMinFitness = 1000;
    private int noOfIterations = 0;

    public int getNoOfIterations() {
        return noOfIterations;
    }

    public HillClimbing(Heuristic heuristicName){
        this.heuristic = heuristicName;

        this.firstState = new PuzzleState(true); // true means initialize random
    }

    public void solve(){

        this.currentState = this.firstState;
        this.currentState.setParent(null);

        while (this.finish == false){
            ArrayList<PuzzleState> newStates = this.currentState.getStatesAfterMoves();

            //Calculate heuristic for new states
            int stateMinFitness = this.globalMinFitness;
            int stateMinIndex   = -1;

            for(int i = 0; i< newStates.size(); i++){
                int currHeuristicFitness = this.heuristic.getFitness(PuzzleState.GOAL, newStates.get(i).getCurrentState());

                if(currHeuristicFitness <  stateMinFitness){
                    stateMinFitness = currHeuristicFitness;
                    this.globalMinFitness = stateMinFitness;
                    stateMinIndex = i;
                }
            }

            if(stateMinIndex != -1) {
                this.currentState = newStates.get(stateMinIndex);
            }else{
                //new min state not found
                this.finish = true;
            }

            newStates.clear();

            if(stateMinFitness == 0){
                this.finish = true;
                this.solutionFound = true;
            }
        }
    }

    public PuzzleState hillClimbingSolve(){
        this.solve();

        if(this.solutionFound == true){
            return this.currentState;
        }else{
            return null;
        }
    }

    @Override
    public String toString() {
        if(this.solutionFound == false){
            return "Solution not found !";
        }
        return this.currentState.toString();
    }
}
