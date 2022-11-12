package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;
import java.util.*;

/**
 * <p>
 * A blurring filter that uses the median value of a group of pixels to determine blur.
 * </p>
 * 
 * @author Put Suthisrisinlpa
 * @author Alex Hopgood - Filter extension
 */

public class MedianFilter implements ImageOperation, java.io.Serializable {
    private int radius;

    /**
     * <p>
     * Create an instance of the MedianFilter operation.
     * </p>
     * By default radius is set to 1, finding the median value of a 3x3 patch.
     */
    MedianFilter() {
        this.radius = 1;
    }


    /**
     * <p>
     * Create an instance of the MedianFilter operation.
     * </p>
     * @param radius The radius of the kernal.
     */
    MedianFilter(int radius) {
        this.radius = radius;

    }

    /**
     * <>
     * Applies the MedianFilter operation to a given image.
     * </>
     * 
     * @param input The image being blurred.
     * @return The blurred image.
     */
    public BufferedImage apply(BufferedImage input) {

        int height = input.getHeight();
        int width = input.getWidth();
        BufferedImage output = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);

        // total pixels in a kernel
        int neighbours = (2 * radius + 1) * (2 * radius + 1);

        // array that contain each rgba values of the inputImage
        int[][] inputRed = new int[height][width];
        int[][] inputGreen = new int[height][width];
        int[][] inputBlue = new int[height][width];
        int[][] inputAlpha = new int[height][width];

        // these array will contain colour channel for each explored pixel in the kernel
        int[] red = new int[neighbours];
        int[] green = new int[neighbours];
        int[] blue = new int[neighbours];
        int[] alpha = new int[neighbours];

        // separate the rgba values of the input image
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Color pixel = new Color(input.getRGB(x, y));
                inputAlpha[y][x] =  pixel.getAlpha();
                inputRed[y][x] = pixel.getRed();
                inputGreen[y][x] = pixel.getGreen();
                inputBlue[y][x] = pixel.getBlue();
            }
        }

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {

                // defining kernel boundaries
                int xMin = x - radius;
                int xMax = x + radius;
                int yMin = y - radius;
                int yMax = y + radius;

                // exploring the current kernel
                int idx = 0;
                for (int yK = yMin; yK <= yMax; ++yK) {

                    for (int xK = xMin; xK <= xMax; xK++) {


                        int xInBound = xK < 0 ? -1 : (xK >= width ? 1 : 0);   // - 1 if x < 0, 0 if x is in bound, 1 if x > width
                        int yInBound = yK < 0 ? -1 : (yK >= height ? 1 : 0);  // - 1 if y < 0, 0 if y is in bound, 1 if y > height
                        int adjustedX = xInBound == 0 ? xK : (xInBound > 0 ? width-1 : 0);
                        int adjustedY = yInBound == 0 ? yK : (yInBound > 0 ? height-1 : 0);


                        alpha[idx] =  inputAlpha[adjustedY][adjustedX];
                        red[idx] =inputRed[adjustedY][adjustedX];
                        green[idx] = inputGreen[adjustedY][adjustedX];
                        blue[idx] = inputBlue[adjustedY][adjustedX];
                        idx++;

                    }
                }

                output.setRGB(x, y, new Color(getMedian(red), getMedian(green), getMedian(blue), getMedian(alpha)).getRGB());
            }
        }
        return output;
    }

    private int getMedian(int[] values){
        Arrays.sort(values);
        int n = values.length;
        // check for even case
        if (n % 2 != 0)
            return values[n / 2];
        return values[(n - 1) / 2] + values[n / 2] /2;
    }
}
