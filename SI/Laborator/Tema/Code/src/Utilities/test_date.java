package Utilities;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dimitrie on 06/01/2016.
 */
public class test_date {
    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());

        while(true){
            System.out.println(calendar.toString());
            calendar2.setTime(new Date());

            if(calendar2.getTimeInMillis() > calendar.getTimeInMillis()){
                System.out.println("Calendar 2 > Calendar 1");
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
