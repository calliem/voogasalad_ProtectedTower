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
    
    public static ImageView snap (Node group, int width, int height){
        WritableImage snapImage = new WritableImage(width, height); // TODO
        return createSnap(snapImage, group, width, height);
    }
    
    public static ImageView snap (GameObject group){
        int width = (int) group.getWidth();
        int height = (int) group.getHeight();
        WritableImage snapImage = new WritableImage(width, height); // TODO
        return createSnap(snapImage, group.getRoot(), width, height);
    }
    
    private static ImageView createSnap(WritableImage snapImage, Node node, int width, int height){
        snapImage = node.snapshot(new SnapshotParameters(), snapImage);
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
