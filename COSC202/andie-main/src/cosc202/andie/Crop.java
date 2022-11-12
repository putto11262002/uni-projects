package cosc202.andie;
import java.awt.image.*;
import java.awt.*;

/**
 * <p>
 * A crop operation that produces a subset of an image based on an area selected by the user.
 * </p>
 * The user must select the area before initialising the crop operation.
 * 
 * @author Suzanna Palacios
 * @author Blaise Turner-Parker
 */
public class Crop implements ImageOperation, java.io.Serializable{
    int x1;
    int y1;
    int x2;
    int y2;

    /**
     * <p>
     * Create a new Crop operation.
     * </p>
     * @param xOne the x coordinate of the first click
     * @param yOne the y coordinate of the first click
     * @param xTwo the x coordinate of the second click
     * @param yTwo the y coordinate of the second click
     */
    public Crop (int xOne, int yOne, int xTwo, int yTwo) {
        x1=xOne;
        y1=yOne;
        x2=xTwo;
        y2=yTwo;
    }

    /**
     * <p>
     * Applies the Crop operation to a given image.
     * </p>
     * @param input The image being cropped.
     * @returns The cropped subset of the original image.
     */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage img = input.getSubimage(x1, y1, x2, y2);
        //create copy of the crop so that any other edits aren't impacting the full image
        //just the crop
        BufferedImage croppedCopy = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = croppedCopy.createGraphics();
        g.drawImage(img, 0, 0, null);
        return croppedCopy;
    }
}