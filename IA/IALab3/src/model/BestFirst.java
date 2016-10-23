package model;

import heuristics.Heuristic;

import java.util.ArrayList;

/**
 * Created by uidj6605 on 10/27/2015.
 */
public class BestFirst implements ProblemSolver {

    private ArrayList<PuzzleState> openStates = new ArrayList<>();
    private ArrayList<PuzzleState> visitedStates = new ArrayList<>();
    private PuzzleState firstState;
    private PuzzleState currentState;

    private Heuristic heuristic;
    private boolean finish = false;
    boolean solutionFound = false;
    int globalMinFitness = 1000;

    public BestFirst(Heuristic heuristicName){
        this.heuristic = heuristicName;
        this.firstState = new PuzzleState(true);
    }

    @Override
    public void solve() {
        this.currentState  = this.firstState;
        this.visitedStates.add(this.currentState);

        if(this.hasSolution() == false){
            return;
        }

        while(this.finish == false) {

            int stateMinFitness = this.globalMinFitness;
            int stateMinIndex   = -1;

            ArrayList<PuzzleState> newStates = this.currentState.getStatesAfterMoves();
            this.updateOpenStates(newStates);

            for(int i = 0; i< this.openStates.size(); i++){
                int currHeuristicFitness = this.heuristic.getFitness(PuzzleState.GOAL, this.openStates.get(i).getCurrentState());

                if(currHeuristicFitness <  stateMinFitness){
                    stateMinFitness = currHeuristicFitness;
                    this.globalMinFitness = stateMinFitness;
                    stateMinIndex = i;
                }
            }

            if(stateMinIndex != -1) {
                this.currentState = this.openStates.get(stateMinIndex);
                this.removeStateFromOpenStates(this.currentState);
                this.visitedStates.add(this.currentState);
            }

            newStates.clear();

            if(stateMinFitness == 0){
                this.finish = true;
                this.solutionFound = true;
            }
        }
    }

    private void updateOpenStates(ArrayList<PuzzleState> newStates){
        for(int i = 0; i < newStates.size(); i++){
            if(! this.openStates.contains(newStates.get(i)) || !this.visitedStates.contains(newStates.get(i))){
                this.openStates.add(newStates.get(i));
            }
        }
    }

    private void removeStateFromOpenStates(PuzzleState stateToRemove){

        for(int i = 0; i< this.openStates.size(); i++){
            if(this.openStates.get(i).equals(stateToRemove)){
                this.openStates.remove(stateToRemove);
            }
        }
    }

    private boolean hasSolution(){
        int[] liniarRepresentationGoal = new int[PuzzleState.GOAL.length* PuzzleState.GOAL.length];
        int[] liniarRepresentationInSt = new int[PuzzleState.GOAL.length* PuzzleState.GOAL.length];

        int indexI = 0;
        int indexJ = 0;

        for(int i = 0; i< PuzzleState.GOAL.length* PuzzleState.GOAL.length; i++){

            if((0 == i%(PuzzleState.GOAL.length)) && (i!= 0)){
                indexI++;
                indexJ = 0;
            }

            liniarRepresentationInSt[i] = this.currentState.getCurrentState()[indexI][indexJ];
            liniarRepresentationGoal[i] = PuzzleState.GOAL[indexI][indexJ];
            indexJ++;
        }
        int noOfInversionsGoal = this.numberOfInversions(liniarRepresentationGoal);
        int noOfInversionsInSt = this.numberOfInversions(liniarRepresentationInSt);

        if((noOfInversionsGoal%2) != (noOfInversionsInSt%2)){
            return false;

        }
        return true;
    }

    private int numberOfInversions(int[] array){
        int noOfInversions = 0;

        for(int i = 0; i< array.length; i++){
            if(array[i] != 0){
                for(int j=i+1; j< array.length; j++){
                    if(array[j] < array[i]){
                        noOfInversions++;
                    }
                }
            }
        }
        return noOfInversions;
    }

    public PuzzleState bestFirstSolve(){
        this.solve();

        if(this.solutionFound == false){
            return null;
        }
        return this.currentState;
    }

    public void bestFirstSolveNTimes(int n){

        int unsolved = 0;
        int solved   = 0;
        for(int i = 0; i< n; i++){
            this.solve();

            if(this.solutionFound == false){
                unsolved++;
            }else{
                solved++;
            }

        }
        System.out.println("Solved : " + solved + ", Unsolved = " + unsolved);
    }
}
