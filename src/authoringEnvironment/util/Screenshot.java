package authoringEnvironment.util;

import authoringEnvironment.objects.GameObject;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class Screenshot {
    
    public static ImageView snap (GameObject group){
        WritableImage snapImage = new WritableImage(group.getWidth(), group.getHeight()); // TODO
        snapImage = group.getRoot().snapshot(new SnapshotParameters(), snapImage);
        ImageView snapView = new ImageView();
        snapView.setImage(snapImage);
        return snapView;    
    }
}
