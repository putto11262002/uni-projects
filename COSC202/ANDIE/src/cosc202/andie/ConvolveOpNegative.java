package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

public class ConvolveOpNegative {
    private Kernel kernel;

    public ConvolveOpNegative(Kernel kernel){
        this.kernel = kernel;
    }

    public void filter(BufferedImage input, BufferedImage output){
        int height = input.getHeight();
        int width = input.getWidth();
        int radius = kernel.getHeight()/2;

        float[] kernelArray = new float[kernel.getHeight() * kernel.getWidth()];
        this.kernel.getKernelData(kernelArray);


        // array that contain each rgba values of the inputImage
        int[][] red = new int[width][height];
        int[][] green = new int[width][height];
        int[][] blue =new int[width][height];
        int[][] alpha = new int[width][height];

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                red[x][y] = r;
                green[x][y] = g;
                blue[x][y] = b;
                alpha[x][y] =a;
            }
        }


        for (int y = 0; y < height; ++y) {

            for (int x = 0; x < width; ++x) {
                int rs = 128;
                int gs = 128;
                int bs = 128;

                int idx = 0;
                for(int i = 0; i < kernel.getHeight(); i++){
                    for(int j = 0; j < kernel.getWidth(); j++){
                        int calX = x - radius + j;
                        int  calY = y - radius + i;
                        if (calY < 0 || calY >= height || calX < 0 || calX >= width) {
                            rs+=red[x][y];
                            gs+=green[x][y];
                            bs+=blue[x][y];
                        }else{

                            rs+=red[calX][calY]*kernelArray[idx];
                            gs+=green[calX][calY]*kernelArray[idx];
                            bs+=blue[calX][calY]*kernelArray[idx];
                        }
                        idx++;
                    }
                }

                int argb = (alpha[x][y]<< 24) | (truncate(rs) << 16) | (truncate(gs) << 8) | truncate(bs);
                output.setRGB(x, y, argb);
            }
        }
    }

    private final int truncate(int c) {
        if      (c <   0) return 0;
        if (c > 255) return 255;
        return c;
    }
}
