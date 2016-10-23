package ro.sortvisualizer.algos;

import ro.sortvisualizer.SortAlgorithm;
import ro.sortvisualizer.SortArray;

/**
 * Created by dimitrie on 29.04.2015.
 */
public class InsertionSort extends SortAlgorithm{

    @Override
    public void sort(SortArray array) {
        array.setInactive(0, array.length());
        for (int i = 0; i < array.length(); i++) {
            for (int j = i; j >= 1 && array.compareAndSwap(j - 1, j); j--);
        }
        array.setDone(0, array.length());
    }

    @Override
    public String getName() {
        return "Insertion sort";
    }
}
