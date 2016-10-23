package ro.sortvisualizer;

/**
 * Created by dimitrie on 27.04.2015.
 */
public interface SortArray {

    int length();

    int compare(int i, int j);

    void swap(int i, int j);

    boolean compareAndSwap(int i, int j);

    /* Sorting progress visualization */
    void setActive(int index);
    void setInactive(int index);
    void setDone(int index);

    void setActive(int start, int end);
    void setInactive(int start, int end);
    void setDone(int start, int end);
}
