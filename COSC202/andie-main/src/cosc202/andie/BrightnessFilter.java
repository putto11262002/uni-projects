package cosc202.andie;
import java.awt.image.*;

/**
 * <p>
 * Filter to adjust the brightness and contrast by a user given percentage.
 * </p>
 * 
 * @author Suzanna Palacios
 */

public class BrightnessFilter implements ImageOperation, java.io.Serializable {
    private double brightness;
    private double contrast;

    /**
     * <p>
     * Create a new BrightnessFilter operation.
     * </p>
     * @param brightness Percent value to adjust the brightness percentage by.
     * @param contrast   Percent value to adjust the contrast by.
     */
    BrightnessFilter(double brightness, double contrast) {
        this.brightness = brightness;
        this.contrast = contrast;

    }

    /**
     * <p>
     * Create a new BrightnessFilter operation with automatic 1% brightness and contrast inputs.
     * </p>
     */
    BrightnessFilter() {
        this(1, 1);

    }

    /**
     * <p>
     * Applies the brightness filter
     * </p>
     * 
     * @param input the image that the filter is being applied to.
     * @returns the resulting image, with brightness and contrast adjusted.
     */
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                // equation for applying the percentage of brightness and contrast
                // Brightness is the mathematical modifier, needs renaming
                // percent is the name for brightness, needs renaming
                double calculation;
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);
                // R value modification
                calculation = (1 + (contrast / 100)) * (r - 127.5) + (127.5 * (1 + (brightness / 100)));

                r = (int) calculation;
                if (r <= 0) {
                    r = 0;
                }
                if (r > 255) {
                    r = 255;
                }
                // G value modification

                calculation = (1 + (contrast / 100)) * (g - 127.5) + (127.5 * (1 + (brightness / 100)));
                g = (int) calculation;
                if (g <= 0) {
                    g = 0;
                }
                if (g > 255) {
                    g = 255;
                }
                // B value modification

                calculation = (1 + (contrast / 100)) * (b - 127.5) + (127.5 * (1 + (brightness / 100)));
                b = (int) calculation;
                if (b <= 0) {
                    b = 0;
                }
                if (b > 255) {
                    b = 255;
                }
                // apply the values
                argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }
        return input;
        // store original colour
        // go through each pixel, multiply by the value asked. Make sure it doesn't go
        // below 0 and above 255 for each RGB
    }

    /**
     * <p>
     * Normalises the values of each colour channel so that each is positive.
     * </p>
     */
    public static class NormalizeNegative {


        /**
         * <p>
         * Normalisation that ensures each colour channel is positive.
         * </p>
         * @param image the image with negative colour values that need to be normalised.
         */
        public static void normalize(BufferedImage image){
            for(int rows = 0; rows < image.getHeight(); rows++){
                for(int cols = 0; cols < image.getWidth(); cols++){
                    int argb = image.getRGB(cols, rows);
                    int a = (argb & 0xFF000000) >> 24;
                    int r = (argb & 0x00FF0000) >> 16;
                    int g = (argb & 0x0000FF00) >> 8;
                    int b = (argb & 0x000000FF);
                    a+=128;
                    b+=128;
                    g+=128;
                    argb = (a << 24) | (r << 16) | (g << 8) | b;
                    image.setRGB(cols, rows, argb);
                }
            }

        }
    }
}
