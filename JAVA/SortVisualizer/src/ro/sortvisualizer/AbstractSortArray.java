package ro.sortvisualizer;

/**
 * Created by dimitrie on 27.04.2015.
 */
public  abstract class AbstractSortArray implements SortArray {

    protected int[] values;

    public AbstractSortArray(int size) {
        this.values = new int[size];

        for(int i = 0; i< this.values.length; i++){
            this.values[i] = i;
        }
    }

    public int length(){return  this.values.length; }

    public int compare(int i, int j){
        /**
         * @Brief 0 - =
         *        -1 values[i] < values[j]
         *        1 otherwise
         */
//        return (this.values[i] == this.values[j])?0:((this.values[i] < this.values[j])?(-1):1);
        if (values[i] < values[j])
            return -1;
        else if (values[i] > values[j])
            return 1;
        else
            return 0;
    }

    public void swap(int  i, int j){
        int temp = this.values[i];
        this.values[i] = this.values[j];
        this.values[j] = temp;
    }

    public boolean compareAndSwap(int i, int j){

        // if values[j] < values[i] -> swap
        if(compare(j, i) < 0){
            swap(i, j);
            return true;
        }else{
            return false;
        }
    }

}
