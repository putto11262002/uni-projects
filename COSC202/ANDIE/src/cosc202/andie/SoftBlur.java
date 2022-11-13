package cosc202.andie;

import java.awt.image.*;
import java.awt.Image;

/**
 * <p>
 * SoftBlur filter
 * </p>
 * 
 * Filter is applied via convolution. The size of the convolution kernal is specified by the radius. Larger radii create stronger blurring.
 * 
 */
public class SoftBlur implements ImageOperation, java.io.Serializable {
  private int radius;
  
  SoftBlur(int radius) {
    this.radius = radius;
  }
  
  SoftBlur(){
    this(1);
  }
  
  /**
   * <p>
   * Apply a SoftBlur filter to the image.
   * </p>
   * 
   * @param input The image to apply the SoftBlur filter to.
   * @return The resulting (softBlurred) image.
   */
  public BufferedImage apply(BufferedImage input) {
    // The values for the kernel as a 9-element array
    float[] array = { 0, 1 / 8.0f, 0, 1 / 8.0f, 1 / 2.0f, 1 / 8.0f, 0, 1 / 8.0f, 0 };
    // Make a 3x3 filter from the array
    Kernel kernel = new Kernel(3, 3, array);
    // Apply this as a convolution - same code as in MeanFilter
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
    /*
     * drawing the unchanged input image onto the same bufferedImage instance as the
     * scaled one.
     * this gives us a border around the input image
     */
    resizedImage.getGraphics().drawImage(input, kerWidth, kerHeight, null);

    /*
     * because we have a border around the image, the kernel no longer tries to
     * access out of bounds pixels
     * (on the original image at least), meaning the border issue doesn't apply to
     * the original
     */
    ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    BufferedImage out = new BufferedImage(resizedImage.getColorModel(), resizedImage.copyData(null),
        input.isAlphaPremultiplied(), null);
    BufferedImage small;

    convOp.filter(resizedImage, out);

    /*
     * border issue still applies to the entire image, so we just crop down to the
     * original image and return it!
     * good as new :)
     */
    small = out.getSubimage(kerWidth, kerHeight, input.getWidth(), input.getHeight());
    return small;
  }
  
  }
  