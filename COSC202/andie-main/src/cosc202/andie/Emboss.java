package cosc202.andie;
import java.awt.image.*;
import java.awt.*;

/**
 * <p>
 * Emboss the edges within an image.
 * </p>
 * 
 * <p>
 * The emboss filter creates the effect of the image being pressed into or raised out of a sheet of paper.
 * </p>
 * 
 * @author Sean Russell
 */

public class Emboss implements ImageOperation, java.io.Serializable {
    double[][] kern;
    int kernNum;

    /**
     * <p>
     * Truncates 'a' so that the ARGB values are within the 0 to 255 range.
     * </p>
     * @param a corresponds to the ARGB value.
     * @return a double within the range of 0 and 255.
     */
    public static double truncate(double a) {
        if      (a <   0) return 0;
        else if (a > 255) return 255;
        else              return a;
    }

    /**
     * <p>
     * Create a new Emboss operation.
     * </p>
     * @param kernNum used to pick which kernal number the user wants for the embossing action.
     */
    Emboss(int kernNum) {
        this.kernNum = kernNum;
    }

    /**
     * <p>
     * Create a new Emboss operation, with default kernNum 1.
     * </p>
     */
    Emboss() {
        this(1);
    }

    /**
     * <p>
     * Applies the Emboss operation.
     * </p>
     * 
     * @param input the image being embossed.
     * @returns the embossed version of the image.
     */
    public BufferedImage apply(BufferedImage input) {
        // eight default kernels for emboss filter

        double[][]kernal1 = {{0, 0, 0}, {1, 0, -1}, {0 ,0, 0}};
        double[][]kernal2 = {{1, 0, 0}, {0, 0, 0}, {0 ,0, -1}};
        double[][]kernal3 = {{0, 1, 0}, {0, 0, 0}, {0 ,-1, 0}};
        double[][]kernal4 = {{0, 0, 1}, {0, 0, 0}, {-1 ,0, 0}};
        double[][]kernal5 = {{0, 0, 0}, {-1, 0, 1}, {0 ,0, 0}};
        double[][]kernal6 = {{-1, 0, 0}, {0, 0, 0}, {0 ,0, 1}};
        double[][]kernal7 = {{0, -1, 0}, {0, 0, 0}, {0 ,1, 0}};
        double[][]kernal8 = {{0, 0, -1}, {1, 0, -1}, {1 ,0, 0}};
        double[][]horizontalSobel = {{-0.5, 0, 0.5}, {-1, 0, 1}, {-0.5 ,0, 0.5}};
        double[][]verticalSobel = {{-0.5, -1, -0.5}, {0, 0, 0}, {0.5 ,1, 0.5}};
        if (kernNum == 1) {
            kern = kernal1;
        } else if (kernNum == 2) {
            kern = kernal2;
        } else if (kernNum == 3) {
            kern = kernal3;
        } else if (kernNum == 4) {
            kern = kernal4;
        } else if (kernNum == 5) {
            kern = kernal5;
        } else if (kernNum == 6) {
            kern = kernal6;
        } else if (kernNum == 7) {
            kern = kernal7;
        } else if (kernNum == 8) {
            kern = kernal8;
        } else if(kernNum==9){
            kern = horizontalSobel;
        }
        else if(kernNum==10){
            kern = verticalSobel;
        }
        else{
            kern = kernal1;
        }

        int inputHeight = input.getHeight();
        int inputWidth = input.getWidth();

        int kerHeight = (2 * 4 + 1) * 2;
        int kerWidth = (2 * 4 + 1) * 2;
;        
        int newWidth = input.getWidth() + kerWidth * 2;
        int newHeight = input.getHeight() + kerHeight * 2;



         // Resize image using the .getScaledInstance method
        Image scaledImage = input.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        // creating the new bufferedImage instance
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        // applying the scaling to the bufferedImage
        resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        /* drawing the unchanged input image onto the same bufferedImage instance as the scaled one.
         this gives us a border around the input image */
        resizedImage.getGraphics().drawImage(input, kerWidth / 2, kerHeight / 2, null);

        /* because we have a border around the image, the kernel no longer tries to access out of bounds pixels
          (on the original image at least), meaning the border issue doesn't apply to the original */

        BufferedImage output = new BufferedImage(resizedImage.getColorModel(), resizedImage.copyData(null),
        input.isAlphaPremultiplied(), null);
       BufferedImage small;

    int width = output.getWidth();
    int height = output.getHeight();


    int[][]red = new int[width][height];
    int[][]green = new int[width][height];
    int[][]blue = new int[width][height];

            for (int y = 0; y < height - 18; ++y) {
                for (int x = 0; x < width - 18; ++x) {
                int argb = output.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                red[x][y] = r;
                green[x][y] = g;
                blue[x][y] = b;

            }
        }
        for(int rows = 1; rows<height - 1; rows++){
            for(int cols =1; cols<width - 1; cols++){
                int argb = output.getRGB(cols, rows);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);
                double redsum = +128;
                double greensum = +128;
                double bluesum = +128;

                for(int i =0; i<3;i++){
                    for(int j = 0; j<3; j++){
                        int rowmath = rows-1+i;
                        int colmath = cols-1+j;
                        redsum += red[colmath][rowmath]*kern[i][j];
                        greensum += green[colmath][rowmath]*kern[i][j];
                        bluesum += blue[colmath][rowmath]*kern[i][j];
                    }
                }

                r = (int)truncate(redsum);
                g = (int)truncate(greensum);
                b = (int)truncate(bluesum);
                argb = (a << 24) | (r << 16) | (g << 8) | b;
                output.setRGB(cols, rows, argb);
            }
        }
        small = output.getSubimage(kerWidth / 2, kerHeight / 2, inputWidth, inputHeight);
        return small;
    }
}