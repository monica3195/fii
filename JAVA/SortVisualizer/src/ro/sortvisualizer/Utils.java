package ro.sortvisualizer;

import java.util.Random;

/**
 * Created by dimitrie on 27.04.2015.
 */
public final class Utils {

    public static final Random randomSeeder = new Random();

    public static void shuffle(int[] values){

        for(int i=values.length-1; i >= 0 ; i--){
            int j = randomSeeder.nextInt(i+1);
            int temp = values[i];
            values[i] = values[j];
            values[j] = temp;
        }
    }

}
