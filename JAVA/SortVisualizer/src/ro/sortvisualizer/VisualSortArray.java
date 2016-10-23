package ro.sortvisualizer;


//Imports
import java.awt.*;
import java.util.Arrays;

/**
 * Created by dimitrie on 27.04.2015.
 */
final class VisualSortArray extends AbstractSortArray{

    //Control
    private boolean isStopRequested;

    /****COLOR AND DESIGNER AND RENDER SPEED******/
    //States
    private final int ACTIVE = 0, INACTIVE = 1, COMPARING = 2, DONE = 3;
    private int[] states; // state 0-active, 1-inactive, 2-comparing, 3-done

    //Color states
    private static Color[] COLORS = {
        new Color(0x000F99), // active - blue
        new Color(0xADA2BF), // inactive - gray
        new Color(0xD46200), // comparing - orange
        new Color(0x339603), // done - green
    };

    //Speed regulations
    private static double MAX_FPS = 60;
    private int stepsToExecute;
    private int stepsSinceRepaint;
    private int paintDelay;
    private boolean lazyDrawingEnabled;

    //Graphics
    private int scale;
    //private Graphics graphics;
    //private BufferedCanvas bufferedCanvas;

    private BufferedCanvas bufferedCanvas;
    private Graphics graphics;

    /**** STATISTICS *****/
    private int localComparisionCount;
    private int localSwapCount;
    private volatile int comparisionCount = 0;
    private volatile int swapCount = 0;


    private static Color BG_COLOR = new Color(0xFFFFFF); // Background color

    public VisualSortArray(int size, int scale, double speed){

        //Set number proprieties
        super(size);
        // Shuffle values
        Utils.shuffle(values);

        //Draw, designer and graphics proprieties
        this.states = new int[size];

        //Set speed regulations
        this.paintDelay = (int)Math.round(1000 / Math.min(MAX_FPS, speed));
        this.stepsToExecute = (int)Math.max(speed / MAX_FPS, 1);
        this.stepsSinceRepaint = 0;
        this.lazyDrawingEnabled = this.stepsToExecute > size;

        this.scale = scale;

        //Statistics (local)
        this.swapCount = 0;
        this.comparisionCount = 0;

        this.bufferedCanvas = new BufferedCanvas(size * scale);
        this.graphics = this.bufferedCanvas.getBufferGraphics();

        this.redraw(0, this.values.length, true);

    }

/* Comparison and swapping */

    public int compare(int i, int j) {
        if (isStopRequested)
            throw new StopException();

        this.localComparisionCount++;
        setIndex(i, 2);
        setIndex(j, 2);
        requestRepaint();

        // No repaint here
        setActive(i);
        setActive(j);

        return super.compare(i, j);
    }


    public void swap(int i, int j) {
        if (isStopRequested)
            throw new StopException();

        super.swap(i, j);
        this.localSwapCount++;

        setActive(i);
        setActive(j);
        requestRepaint();
    }


    public void requestRepaint(){
        this.stepsSinceRepaint++;
        if(this.stepsSinceRepaint >= this.stepsToExecute ){
            if(lazyDrawingEnabled)
                this.redraw(0, this.values.length, true);
                this.bufferedCanvas.repaint();

                try{
                    Thread.sleep(paintDelay);
                }catch (InterruptedException e){
                    throw new StopException();
                }
            this.stepsSinceRepaint = 0;
        }
    }

    private void redraw(int start, int end, boolean forced){
        if(!forced && lazyDrawingEnabled){
            return;
        }

        this.graphics.setColor(BG_COLOR);
        this.graphics.fillRect(0, start * this.scale, values.length * scale, (end - start) * this.scale);

        if(this.scale == 1){
            for (int i = start; i < end; i++) {
                graphics.setColor(COLORS[states[i]]);
                graphics.drawLine(0, i, values[i], i);
            }
        }else{
            for (int i = start; i < end; i++) {
                graphics.setColor(COLORS[states[i]]);
                graphics.fillRect(0, i*scale, (values[i] + 1)* scale, scale);
            }
        }
    }

    public void assertSort(){
        for(int i = 1; i < this.values.length; i++){
            if(this.values[i-1] > this.values[i]){ // check if is unordered
                throw new AssertionError();
            }
        }
        this.redraw(0, this.values.length, true);
        this.bufferedCanvas.repaint();
    }

    public int getLocalComparisionCount() {
        return localComparisionCount;
    }

    public int getLocalSwapCount() {
        return localSwapCount;
    }

    public void requestStop(){
        this.isStopRequested = true;
    }

    public Canvas getCanvas(){
        return this.bufferedCanvas;
    }

    private void setIndex(int index, int state){
        this.states[index] = state;
        redraw(index, index + 1, false);
    }

    public void setRange(int start, int stop, int state){
        Arrays.fill(states, start, stop, state);
        this.redraw(start, stop, false);
    }

    /* Array visualization */

    public void setActive  (int index) { setIndex(index, ACTIVE); }
    public void setInactive(int index) { setIndex(index, INACTIVE); }
    public void setDone    (int index) { setIndex(index, DONE); }

    public void setActive  (int start, int end) { setRange(start, end, ACTIVE); }
    public void setInactive(int start, int end) { setRange(start, end, INACTIVE); }
    public void setDone    (int start, int end) { setRange(start, end, DONE); }
}
