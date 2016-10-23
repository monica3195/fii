package model;

import heuristics.Heuristic;
import heuristics.OutOfPlace;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by uidj6605 on 10/28/2015.
 */
public class AStar implements ProblemSolver {

    private Heuristic heuristic;
    private Heuristic hammingDistance;
    private PuzzleState currentNode;
    private LinkedList<PuzzleState> closedSet = new LinkedList<>();
    private PriorityQueue<PuzzleState> openSet = new PriorityQueue<>();
    private int counter = 0;


    public AStar(Heuristic heuristicName){
        this.heuristic = heuristicName;
        this.currentNode = new PuzzleState(true);
        this.hammingDistance = new OutOfPlace();
    }

    public PuzzleState aStarSolver(){
        //Set fitness for first node
        this.openSet.offer(this.currentNode);

        while (!this.openSet.isEmpty()){

            PuzzleState q = this.openSet.poll();

            if(0 == this.hammingDistance.getFitness(q.getCurrentState(), PuzzleState.GOAL)){
                return q;
            }
            this.closedSet.add(q);

            ArrayList<PuzzleState> successors  = q.getStatesAfterMoves();

            for(PuzzleState s : successors){
                if(0 == this.hammingDistance.getFitness(s.getCurrentState(), PuzzleState.GOAL)){
                    return s;
                }

                float tentativeG = q.getG() + this.heuristic.getFitness(s.getCurrentState(), s.getCurrentState());
                float tentativeF = tentativeG + this.heuristic.getFitness(PuzzleState.GOAL, s.getCurrentState());

                boolean closedSkip = false;
                for(PuzzleState closedState : this.closedSet){

                    if (this.hammingDistance.getFitness(closedState.getCurrentState(), s.getCurrentState()) == 0){
                        closedSkip = true;
                        break;
                    }
                }
                if (closedSkip){
                    continue;
                }
                boolean notInOpen = true;
                boolean smaller   = true;

                for(PuzzleState openedState : this.openSet){
                    if(0 == this.hammingDistance.getFitness(openedState.getCurrentState(), s.getCurrentState())){
                        notInOpen = false;
                        break;
                    }
                }

                for(PuzzleState openedState : this.openSet){
                    if(s.getF() < openedState.getF()){
                        smaller = true;
                        break;
                    }
                }

                if(notInOpen || smaller){
                    s.setParent(q);
                    s.setG(tentativeG);
                    s.setF(tentativeF);

                    if(notInOpen){
                        this.openSet.offer(s);
                    }
                }
            }
        }
        return null;
    }

    public PuzzleState getCurrentNode() {
        return currentNode;
    }

    @Override
    public void solve() {
        PuzzleState solution = this.aStarSolver();

        if(null != this.aStarSolver()){
            System.out.println(solution.toString());
        }else{
            System.out.println("No solution found !");
        }
    }

}
