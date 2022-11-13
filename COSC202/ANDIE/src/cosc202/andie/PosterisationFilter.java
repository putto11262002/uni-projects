package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * Posterisation of an image. This reduces the number of unique colours used in the image.
 * </p>
 * 
 * @author Sean Russell
 */
public class PosterisationFilter implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create an instance of the Posterisation operation.
     * </p>
     */
    PosterisationFilter() {
    }

    /**
     * <p>
     * Applies the Posterisation operation to a given image.
     * </p>
     * 
     * @param input The image selected to be posterised.
     * @return The same image once it has been posterised.
     */
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                //get the pictures argb values and change them to the nearest int of 0, 128 and 256.
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);
                // R value modification
                if (r <= 64) {
                    r = 0;
                } else if (r > 64 & r < 192) {
                    r = 128;
                } else {
                    r = 256;
                }
                // g value
                if (g <= 64) {
                    g = 0;
                } else if (g > 64 & g < 192) {
                    g = 128;
                } else {
                    g = 256;
                }
                // b value
                if (b <= 64) {
                    b = 0;
                } else if (b > 64 & b < 192) {
                    b = 128;
                } else {
                    b = 256;
                }
                argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }
        return input;
        // store original colour

        // below 0 and above 255 for each RGB
    }
}
