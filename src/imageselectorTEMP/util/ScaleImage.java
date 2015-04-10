package imageselectorTEMP.util;

import javafx.scene.image.ImageView;

/**
 * Utility class for ImageSelector that can be used to scale images to
 * specified dimensions.
 * 
 * @author Kevin He
 *
 */
public class ScaleImage {
    public static void scaleByHeight(ImageView image, double fitHeight){
        image.setFitHeight(fitHeight);
        preserveImageRatio(image);
    }
    
    public static void scaleByWidth(ImageView image, double fitWidth){
        image.setFitWidth(fitWidth);
        preserveImageRatio(image);
    }
    
    public static void scale(ImageView image, double fitWidth, double fitHeight){
        scaleByHeight(image, fitHeight);
        scaleByWidth(image, fitWidth);
    }

    private static void preserveImageRatio (ImageView image) {
        image.setPreserveRatio(true);
        image.setSmooth(true);
    }
}