package cosc202.andie;

import java.awt.Graphics;
import java.awt.*;
import java.awt.image.*;

/**
 * <p>
 * Draws a line diagonally through a selected rectangle.
 * </p>
 * The area must be selected by the user before attempting to draw the line.
 * 
 * @author Suzanna Palacios
 * @author Alex Hopgood
 */
public class drawLine implements ImageOperation, java.io.Serializable {

    private Color c;
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * <p>
     * Creates an instance of the line drawing operation within a selected rectangle.
     * </p>
     * @param co The colour of the line.
     * @param x The x coordinate of the upper left corner of the rectangle.
     * @param y The y coordinate of the upper left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     */
    drawLine(Color co, int x, int y, int width, int height) {
        this.c = co;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    /**
     * <p>
     * Applies the line drawing operation to a given image.
     * </p>
     * @param input The image being drawn on.
     * @return The image after having a line drawn.
     */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage img = input;
        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics g = output.createGraphics();
        g.setColor(c);
        g.drawImage(img, 0, 0, null);     
        g.drawLine(x, y, (x + width), (y + height)); // (x, y, width, height) 
        
        return output;
    }
}
