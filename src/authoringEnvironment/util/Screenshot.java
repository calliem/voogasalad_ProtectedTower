package authoringEnvironment.util;

import java.io.File;
import javax.imageio.ImageIO;
import authoringEnvironment.objects.GameObject;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class Screenshot {
    
    public static ImageView snap (GameObject group){
        WritableImage snapImage = new WritableImage((int) group.getWidth(), (int) group.getHeight()); // TODO
        snapImage = group.getRoot().snapshot(new SnapshotParameters(), snapImage);
        ImageView snapView = new ImageView();
        snapView.setImage(snapImage);
        
        
        return snapView;    
    }
    
    /*public static String snapAndSave(GameObject group, String fileName){
        File file = new File(fileName + ".png");
        
        
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }*/
    
    /*private String saveAsPng(GameObject object) {

        // TODO: probably use a file chooser here
        File file = new File("chart.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }*/
}
