package cosc202.andie;

import java.awt.image.*;
import java.awt.*;

/**
 * <p>
 * Sharpens an image so that all pixel values exist within 0 and 255.
 * </p>
 * 
 * @author Alex Hopgood
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {


    private boolean normalizeNegative  = false;
    /**
     * <p>
     * Create an instance of the Sharpen operation.
     * </p>
     * 
     * Default constructor. Doesn't change the sharpness.
     */
    SharpenFilter() {
    }

    /**
     * <p>
     * Create an instance of the Sharpen operation.
     * </p>
     * @param normalizeNegative determines whether or not the filter changes anything. If it is set to 'true' the sharpen is applied.
     */
    SharpenFilter(boolean normalizeNegative) {
        this.normalizeNegative = normalizeNegative;
    }

    /**
     * <p>
     * Determines whether the filter will be applied.
     * </p>
     * @param normalizeNegative must be true for the filter to clip the values between 0 and 255.
     */
    public void setNormalizeNegative(boolean normalizeNegative){
        this.normalizeNegative = normalizeNegative;
    }

    /**
     * <p>
     * Apply a sharpen filter to an image.
     * </p>
     * 
     * @param input The image to apply the sharpen filter to.
     * @return The resulting sharpened image.
     */

    public BufferedImage apply(BufferedImage input) {

        float[] kern = new float[] { 
            0.0f, -0.5f,  0.0f,
            -0.5f, 3.0f, -0.5f,                    
            0.0f, -0.5f,  0.0f                
        };  

      Kernel kernel = new Kernel(3, 3, kern); 

      int newWidth = input.getWidth() + (kernel.getWidth() * 2);
      int newHeight = input.getHeight() + (kernel.getHeight() * 2);

      int kerWidth = kernel.getWidth();
      int kerHeight = kernel.getHeight();
 

      // Resize image using the .getScaledInstance method
      Image scaledImage = input.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
      // creating the new bufferedImage instance
      BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
      // applying the scaling to the bufferedImage
      resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
      /* drawing the unchanged input image onto the same bufferedImage instance as the scaled one.
         this gives us a border around the input image */
      resizedImage.getGraphics().drawImage(input, kerWidth, kerHeight, null);

       /* because we have a border around the image, the kernel no longer tries to access out of bounds pixels
          (on the original image at least), meaning the border issue doesn't apply to the original */

      BufferedImage out = new BufferedImage(resizedImage.getColorModel(), resizedImage.copyData(null),
              input.isAlphaPremultiplied(), null);
      BufferedImage small;

        if(!normalizeNegative){
            ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            convOp.filter(resizedImage, out);

        }else{
            ConvolveOpNegative convOp = new ConvolveOpNegative(kernel);
            convOp.filter(resizedImage, out);
        }

        
      /*border issue still applies to the entire image, so we just crop down to the original image and return it!
      good as new :) */
      small = out.getSubimage(kerWidth, kerHeight, input.getWidth(), input.getHeight());
      return small;
    }

}
