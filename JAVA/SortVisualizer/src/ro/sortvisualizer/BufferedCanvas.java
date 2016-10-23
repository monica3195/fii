package ro.sortvisualizer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dimitrie on 27.04.2015.
 */
final class BufferedCanvas extends Canvas {

    private BufferedImage bufferedImage;
    private Graphics graphics;

    public BufferedCanvas(int size) {
        this.setSize(size, size);
        this.bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_BGR);
        this.graphics = this.bufferedImage.getGraphics();
    }

    public Graphics getBufferGraphics() {
        return this.graphics;
    }

    public void update(Graphics graphics){
        paint(graphics);
    }

    public void paint(Graphics graphics){
        graphics.drawImage(bufferedImage, 0, 0, this);
    }

}
