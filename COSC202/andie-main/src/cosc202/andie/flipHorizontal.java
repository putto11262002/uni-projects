package cosc202.andie;
import java.awt.image.*;

/**
 * <p>
 * Class used to mirror every pixels location Horizontally giving us a horizontally flipped image.
 * </p>
 * 
 * @author Sean Russell
 */

public class flipHorizontal implements ImageOperation, java.io.Serializable {
    
    /**
     * <p>
     * Creates a new flipHorizontal operation.
     * </p>
     */
    flipHorizontal() {

    };

    /**
     * <p>
     * Applies the flipHorizontal operation.
     * </p>
     * @param input the image being flipped horizontally.
     * @returns the image after being flipped horizontally.
     */
    public BufferedImage apply(BufferedImage input) {
        // mirrors every pixels X coordinate.
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                flipped.setRGB((width - 1) - x, y, input.getRGB(x, y));
            }
        }
        return flipped;
    }
}
