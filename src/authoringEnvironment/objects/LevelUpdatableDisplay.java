package authoringEnvironment.objects;

import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import authoringEnvironment.Controller;
import authoringEnvironment.NoImageFoundException;
import authoringEnvironment.map.MapWorkspace;


public class LevelUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;
    private Controller myController;

    public LevelUpdatableDisplay (ObservableList<GameObject> observableList,
                                  int rowSize,
                                  double thumbnailSizeMultiplier,
                                  MapWorkspace mapWorkspace) {
        super(observableList, rowSize, thumbnailSizeMultiplier);
        myMapWorkspace = mapWorkspace;
    }

    public LevelUpdatableDisplay (Controller c,
                                  String partType,
                                  int rowSize,
                                  double thumbnailSizeMultiplier, MapWorkspace mapWorkspace) {
        super(c, partType, rowSize, thumbnailSizeMultiplier);
        myMapWorkspace = mapWorkspace;
        myController = c;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);        // create a new map
        try {
            myMapWorkspace.getChildren().add(new ImageView(myController.getImageForKey(object
                                                     .getKey())));
        }
        catch (NoImageFoundException e) {
            e.printStackTrace();
        }
    }

}
