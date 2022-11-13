package cosc202.andie;
import java.awt.image.*;
import java.awt.Image;

/**
 * <p>
 * Blurs the image by applying a Gaussian distribution of selected strength.
 * </p>
 * 
 * @author Blaise Turner-Parker
 * @author Alex Hopgood (filter extension)
 */

public class GaussianBlur implements ImageOperation, java.io.Serializable {
    private int radius;

    /**
     * <p>
     * Create an instance of the GaussianBlur operation.
     * </p>
     * @param radius the size of the pixel radius being used to create the blur effect.
     */
    GaussianBlur(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Create a generic GaussianBlur operation with the minimum radius.
     * </p>
     */
    GaussianBlur() {
        this(1);
    }

    /**
     * <p>
     * Performs the Gaussian calculation on each pixel in the radius
     * </p>
     * 
     * @param x 
     * @param y 
     * @param variance based on the radius input, the severity of the variance from the original values.
     * @return the weight of the change to the pixel based on the GaussianCalculation.
     */
    public float gaussianCalculation(int x, int y, double variance) {
        return (float) (1 / (2 * Math.PI * Math.pow(variance, 2))
                * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(variance, 2))));
    }

    /**
     * <p>
     * Applies the GaussianFilter operation.
     * </p>
     * @param input The image being blurred.
     * @return The image after being blurred.
     */
    public BufferedImage apply(BufferedImage input) {
        // determine the dimensions of the matrix
        int matrixSize = (2 * radius + 1);
        // determine the length of the array
        int arraySize = (2 * radius + 1) * (2 * radius + 1);
        // create the matrix
        float[][] weights = new float[matrixSize][matrixSize];
        // create the matrix for the kernal
        float[] array = new float[arraySize];
        // keep track of the sum for normalisation later
        double sum = 0;

        /*
         * calculate the weights by cycling through the matrix,
         * centring the calculation on the middle of the matrix
         */
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = gaussianCalculation((i - (radius / 2)), (j - (radius / 2)), radius / 3);
                sum += weights[i][j];
            }
        }
        /*
         * normalise the weights
         */
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] /= sum;
            }
        }

        /*
         * fill the kernal array from the weights matrix
         */
        // counting variable
        int count = 0;

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                array[count] = weights[i][j];
                count++;
            }
        }

        Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, array);

        int newWidth = input.getWidth() + (kernel.getWidth() * 2);
        int newHeight = input.getHeight() + (kernel.getHeight() * 2);

        int kerWidth = kernel.getWidth();
        int kerHeight = kernel.getHeight();

        // using the .getScaledInstance method
        Image scaledImage = input.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        // creating the new bufferedImage instance
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        // applying the scaling to the bufferedImage
        resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);

        resizedImage.getGraphics().drawImage(input, kerWidth, kerHeight, null);

        ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage out = new BufferedImage(resizedImage.getColorModel(), resizedImage.copyData(null),
                input.isAlphaPremultiplied(), null);
        BufferedImage small;

        try {
          convOp.filter(resizedImage, out);
          
        } catch (ImagingOpException e) {
            System.out.println("Currently only working with radius 3+ :" + e.getLocalizedMessage());
        }
        small = out.getSubimage(kerWidth, kerHeight, input.getWidth(), input.getHeight());
        return small;
    }
}
