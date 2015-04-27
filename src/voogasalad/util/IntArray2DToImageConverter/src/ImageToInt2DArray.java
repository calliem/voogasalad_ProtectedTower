package voogasalad.util.IntArray2DToImageConverter.src;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ImageToInt2DArray {

        public static int[][] convertImageTo2DIntArray(Image image, int width, int height) {
                
                int[][] returnArray = new int[height][width];
                
                // Obtain PixelReader
        PixelReader pixelReader = image.getPixelReader();
        
        int xSegmentLength = divideAByBThenRoundDown(image.getWidth(), width);
        int ySegmentLength = divideAByBThenRoundDown(image.getHeight(), height);
        
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        
        for (int row = 0; row < height; row++) {
                
                for (int column = 0; column < width; column++) {
                        
                        BufferedImage tempBufferedImage = bufferedImage.getSubimage(xSegmentLength*column, ySegmentLength*row, xSegmentLength, ySegmentLength);
                        
                        Color averageColor = AverageColorFinder.findAverageColorFromImage(SwingFXUtils.toFXImage(tempBufferedImage, null));
                        
                        int argb = ((int)(averageColor.getOpacity()*255) << 24) | 
                                        ((int)(averageColor.getRed()*255) << 16) | 
                                        ((int)(averageColor.getGreen()*255) << 8) | 
                                        ((int)(averageColor.getBlue()*255));
                        
                        returnArray[row][column] = argb;
                        
                }
                
        }
        
        return returnArray;
        
        }
        
        private static int divideAByBThenRoundDown(double a, double b) {
                return (int)Math.floor(a/b);
        }
        
        private static int roundDownToInt(double d) {
                return (int)Math.floor(d);
        }
        
        
}
