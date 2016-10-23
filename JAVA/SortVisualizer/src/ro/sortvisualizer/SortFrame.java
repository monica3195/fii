package ro.sortvisualizer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dimitrie on 28.04.2015.
 */
final class SortFrame extends Frame {

    private SortThread thread;


    public SortFrame(String algorithmName, Component component,SortThread thread){
        super(algorithmName);
        this.thread = thread;
        this.add(component);
        this.setResizable(false);

        pack();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SortFrame.this.thread.requestStop();
           //     dispose();
            }
        });

        Rectangle rect = getGraphicsConfiguration().getBounds();
        setLocation((rect.width - getWidth()) / 8, (rect.height - getHeight()) / 8);
        setVisible(true);
    }
}
