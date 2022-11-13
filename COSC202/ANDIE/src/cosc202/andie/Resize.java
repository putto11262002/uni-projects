package cosc202.andie;

import java.awt.Image;
import java.awt.image.*;

/**
 * <p>
 * Resizes an image to a percentage of its previous size (larger or smaller).
 * </p>
 * 
 * @author Blaise Turner-Parker
 */

public class Resize implements ImageOperation, java.io.Serializable {
    private double proportion;

    /**
     * <p>
     * Creates a new Resize operation.
     * </p>
     * 
     * @param proportion the percentage change in the size of the image (from 0 to over 100% of its current size).
     */
    Resize(double proportion) {
        this.proportion = proportion;

    }

    /**
     * <>
     * Applies the resize operation to a given image.
     * </>
     * 
     * Scales the given image to the proportion input by the user.
     * 
     * @param input The image to be resized.
     * @return The input image after resizing.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // access the current image proportions
        int currentWidth = input.getWidth();
        int currentHeight = input.getHeight();

        // create the new proportions of the image
        int newWidth = (int) (currentWidth * proportion);
        int newHeight = (int) (currentHeight * proportion);

        // using the .getScaledInstance method
        Image scaledImage = input.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        // creating the new bufferedImage instance
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        // applying the scaling to the bufferedImage
        resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        // return
        return resizedImage;
    }
}
