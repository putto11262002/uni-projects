package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * A filter which inverts the colours of an image
 * </p>
 * @author Sean Russell
 */

public class InversionFilter implements ImageOperation, java.io.Serializable {
    /**
     * <p>
     * Creates an instance of the InversionFilter operation.
     * </p>
     */
    InversionFilter() {
    }

    /**
     * <p>
     * Applies the inversion filter to a given image.
     * </p>
     * @param input an image the filter is applied to.
     * @return the input image with its colour channels reversed.
     */
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                //Inverts the colours of an image
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);
                // Switch RGB values to the inverted equivalent
                r = 255-r;
                g = 255-g;
                b = 255-b;
                argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }
        return input;
        // store original colour


    }
}