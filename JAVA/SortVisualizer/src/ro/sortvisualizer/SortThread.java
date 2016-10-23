package ro.sortvisualizer;

/**
 * Created by dimitrie on 28.04.2015.
 */
final class SortThread extends Thread{

    private SortAlgorithm sortAlgorithm;
    private VisualSortArray array;

    public SortThread(VisualSortArray array, SortAlgorithm algorithm) {
        this.array = array;
        this.sortAlgorithm = algorithm;
        new SortFrame(algorithm.getName(), array.getCanvas(), this);
        //add SortFrame
    }

    public void run(){
        try{
            Thread.sleep(100);
            this.sortAlgorithm.sort(this.array);
            try{
                this.array.assertSort();
                System.out.printf("Algorithm : %s, comparisons %d, swaps %d \n", this.sortAlgorithm.getName(), this.array.getLocalComparisionCount(), this.array.getLocalSwapCount());
            }catch (AssertionError e){
                System.out.printf("Algorithm : %s sort failed \n", this.sortAlgorithm.getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void requestStop(){
        //this.interrupt();
        this.array.requestStop();
    }
}
