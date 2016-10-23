/**
 * Created by uidj6605 on 11/10/2015.
 */
public class Point {
    int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
