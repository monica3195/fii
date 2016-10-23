package esaWkipedia;

import java.awt.EventQueue;

import javax.swing.JFrame;



/**
 * @version 
 * @author Victor Tintar
 */
public class CircleLayoutTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               CircleLayoutFrame frame = new CircleLayoutFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //   frame.function(nameConcept,nr);
       		
               frame.setVisible(true);
               
            }
         });
      
  
   }
   
}

/**
 * A frame that shows buttons arranged along a circle.
 */
