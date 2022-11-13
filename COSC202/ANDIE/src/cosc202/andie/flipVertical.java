package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * Method to mirror every pixels location Vertically giving us a vertically flipped image.
 * </p>
 * 
 * @author Sean Russell
 */
public class flipVertical implements ImageOperation, java.io.Serializable {
    /**
     * <p>
     * Creates a new flipVertical operation.
     * </p>
     */
    flipVertical() {

    };

    /**
     * <p>
     * Applies the flipVertical operation.
     * </p>
     * @param input the image being flipped vertically.
     * @returns the image after being flipped vertically.
     */
    public BufferedImage apply(BufferedImage input) {
        // changes the coordinate of every pixel's Y value so it is mirrored.
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                flipped.setRGB(x, (height - 1) - y, input.getRGB(x, y));
            }
        }
        return flipped;
    }
}