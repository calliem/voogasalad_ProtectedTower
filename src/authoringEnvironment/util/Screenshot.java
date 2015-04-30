package authoringEnvironment.util;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import authoringEnvironment.objects.GameObject;

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
    
}
