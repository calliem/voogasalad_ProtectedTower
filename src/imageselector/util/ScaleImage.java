package imageselector.util;

import javafx.scene.image.ImageView;

/**
 * Utility class for ImageSelector that can be used to scale images to
 * specified dimensions.
 * 
 * @author Kevin He
 * @author Bojia Chen
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
        double originalHeight = image.getImage().getHeight();
        double originalWidth = image.getImage().getWidth();
        double scaleHeight = fitHeight/originalHeight;
        double scaleWidth = fitWidth/originalWidth;
        double scaleAll = Math.min(scaleHeight, scaleWidth);
        image.setFitHeight(originalHeight*scaleAll);
        image.setFitWidth(originalWidth*scaleAll);
    }
    
    public static void scaleNoPreserve(ImageView image, double fitWidth, double fitHeight){
        double originalHeight = image.getImage().getHeight();
        double originalWidth = image.getImage().getWidth();
        double scaleHeight = fitHeight/originalHeight;
        double scaleWidth = fitWidth/originalWidth;
        image.setFitHeight(originalHeight*scaleHeight);
        image.setFitWidth(originalWidth*scaleWidth);
    }

    private static void preserveImageRatio (ImageView image) {
        image.setPreserveRatio(true);
        image.setSmooth(true);
    }
}
