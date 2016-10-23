package model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by uidj6605 on 10/27/2015.
 */
public class EightPuzzleStateNode implements Comparable{

    private PuzzleState thisState;
    private EightPuzzleStateNode parent;

    @Override
    public int compareTo(Object o) {
        EightPuzzleStateNode q = (EightPuzzleStateNode)o;

        return this.thisState.compareTo(q.getThisState());
    }

    public EightPuzzleStateNode(PuzzleState s){
        this.thisState = s;
        this.parent = null;
    }

    public PuzzleState getThisState() {
        return thisState;
    }

    public void setParent(EightPuzzleStateNode parent) {
        this.parent = parent;
    }

    public EightPuzzleStateNode(PuzzleState s, EightPuzzleStateNode parent){

        thisState = s;
        this.parent = parent;
    }

    public EightPuzzleStateNode getParent(){
        return parent;
    }

    public LinkedList<EightPuzzleStateNode> getSuccessors(EightPuzzleStateNode q){
        LinkedList<EightPuzzleStateNode> successors = new LinkedList<>();
        ArrayList<PuzzleState> nextStates = q.getThisState().getStatesAfterMoves();

        for(PuzzleState nextState : nextStates){
            successors.add(new EightPuzzleStateNode(nextState));
        }
        return successors;
    }
}
