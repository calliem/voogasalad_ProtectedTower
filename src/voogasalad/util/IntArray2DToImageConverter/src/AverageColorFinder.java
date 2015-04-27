package voogasalad.util.IntArray2DToImageConverter.src;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;


/**
 * A simple, static class that has one (static) method, "findAverageColorFromImage(Image image)"
 * 
 * Call this to return a JavaFX Color (from a given Image) that is the average color of all pixels
 * in the given Image.
 * 
 * 
 * @author Ruslan
 *
 */
public class AverageColorFinder {

    public static Color findAverageColorFromImage (Image image) {

        // Obtain PixelReader
        PixelReader pixelReader = image.getPixelReader();

        double runningR = 0.0, runningG = 0.0, runningB = 0.0, runningAlpha = 0.0;
        int pixels = 0;

        // Determine the color of each pixel in the image
        for (int readY = 0; readY < image.getHeight(); readY++) {

            for (int readX = 0; readX < image.getWidth(); readX++) {

                Color color = pixelReader.getColor(readX, readY);

                // Sanity - check
                // if (color.getRed() > 1.0 || color.getBlue() > 1.0 || color.getGreen() > 1.0)
                // System.out.println("TOO MUCH");

                runningR += color.getRed();
                runningG += color.getGreen();
                runningB += color.getBlue();
                runningAlpha += color.getOpacity();

                pixels++;

            }

        }

        return new Color(runningR / pixels, runningG / pixels, runningB / pixels, runningAlpha /
                                                                                  pixels);

    }

}
