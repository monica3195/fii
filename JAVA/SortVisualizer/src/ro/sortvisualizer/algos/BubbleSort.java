package ro.sortvisualizer.algos;

import ro.sortvisualizer.SortAlgorithm;
import ro.sortvisualizer.SortArray;

/**
 * Created by dimitrie on 27.04.2015.
 */
public class BubbleSort extends SortAlgorithm {

    @Override
    public void sort(SortArray arrayToSort) {
        for(int i = arrayToSort.length(); i >= 1; i--){
            for(int j = 0;  j < i-1; j++ ){
                arrayToSort.compareAndSwap(j, j+1);
            }
            arrayToSort.setDone(i-1);
        }
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}
