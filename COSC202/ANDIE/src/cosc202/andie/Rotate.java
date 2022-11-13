package cosc202.andie;

import java.awt.*;
import java.awt.image.*;

/**
 * <p>
 * Rotate a given image based on degree of rotation input by user.
 * </p>
 * 
 * @author Put Suthisrisinlpa
 */
public class Rotate implements ImageOperation, java.io.Serializable {


    private double theta, sinTheta, cosTheta;
    private final double DEFAULT_THETA= 90;

    /**
     * <p>
     * Create an instance of the Rotation operation.
     * </p>
     * This default constructor rotates the image 90 degrees.
     */
    Rotate() {
        this.theta = DEFAULT_THETA;
        this.sinTheta =Math.abs( Math.sin(Math.toRadians(DEFAULT_THETA)));
        this.cosTheta = Math.abs(Math.cos(Math.toRadians(DEFAULT_THETA)));
    }

    /**
     * <p>
     * Create an instance of the Rotation operation.
     * </p>
     * @param theta Angle of rotation in degree
     */
    Rotate(double theta) {
        this.theta = theta;
        this.sinTheta = Math.abs(Math.sin(Math.toRadians(theta)));
        this.cosTheta = Math.abs(Math.cos(Math.toRadians(theta)));
    }


    /*
     *   public BufferedImage apply(BufferedImage input) {
     *
     *          int inputHeight = input.getHeight(),
     *                  inputWidth = input.getWidth();
     *          int heightR = getRotatedHeight(inputWidth,inputHeight),
     *                  widthR= getRotatedWidth(inputWidth,inputHeight);
     *          BufferedImage output = new BufferedImage(widthR,heightR, input.getType());
     *
     *          for(int y = 0; y < inputHeight; y++){
     *              for(int x = 0; x < inputWidth; x++){
     *
     *                  // translate
     *                  int xT =  x - inputWidth/2;
     *                  int yT =  y - inputHeight/2;
     *                  int xR = rotateX(xT, yT) + widthR/2;
     *                  int yR = rotateY(xT,yT) + heightR/2;
     *
     *                  if(!isValidCoordinate(xR,yR, output)) continue;
     *
     *                  output.setRGB(xR, yR, input.getRGB(x,y));
     *
     *              }
     *          }
     *
     *          return output;
     *      }
     */


    /**
     * <p>
     * Create an instance of the Rotation operation.
     * </p>
     * 
     * Note this implementation is preferred over another one as it is more readable.
     *
     * @param input The image that is to be rotated.
     * @return The image once it has been rotated by theta.
     *
     */
    public BufferedImage apply(BufferedImage input) {

        int height = input.getHeight(),
                width = input.getWidth();
        int heightR = getRotatedHeight(width,height),
                widthR= getRotatedWidth(width,height);
        BufferedImage output = new BufferedImage(widthR,heightR, input.getType());
        // x and y translation of the origin
        int xT =  (widthR - width)/2;
        int yT =  (heightR - height)/2;

        // Create 2D graphics provide more control over the image e.g. translate,and rotate
        Graphics2D graphic = output.createGraphics();
        // Translate  origin of the image by xT, yT
        graphic.translate(xT, yT);
        // rotate by theta around the center
        graphic.rotate(Math.toRadians(this.theta),width/2,height/2);
        graphic.drawRenderedImage(input, null);
        graphic.dispose();

        return output;
    }

    /**
     * <p>
     * Calculates the height of the output image after rotation.
     * </p>
     * 
     * @param originalWidth the width of the input image before rotation.
     * @param originalHeight the height of the input image before rotation.
     * @return the height of the image after rotation.
     */
    private int getRotatedHeight(int originalWidth, int originalHeight){
        return (int) Math.floor(cosTheta * originalHeight + sinTheta * originalWidth);

    }

    /**
     * <p>
     * Calculates the width of the output image after rotation.
     * </p>
     * @param originalWidth the width of the input image before rotation.
     * @param originalHeight the height of the input image after rotation.
     * @return the width of the image after rotation.
     */
    private int getRotatedWidth(int originalWidth, int originalHeight){
        return (int) Math.floor(cosTheta * originalWidth + sinTheta * originalHeight);
    }


    /*
     * private boolean isValidCoordinate(int x, int y,BufferedImage image ){
     *         int height = image.getHeight();
     *         int width = image.getWidth();
     *         return   y > 0 && y < height && x > 0 && x < width;
     *     }
     *
     *     private int rotateX(int x, int y){
     *         return (int) Math.floor(cosTheta * x -  sinTheta * y);
     *
     *     }
     *
     *     private int rotateY(int x, int y){
     *         return (int) Math.floor(cosTheta * y +  sinTheta * x);
     *     }
     */


}