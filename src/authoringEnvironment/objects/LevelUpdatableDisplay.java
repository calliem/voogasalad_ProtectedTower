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
    private LevelSidebar sidebar;

    public LevelUpdatableDisplay (ObservableList<GameObject> observableList,
                                  int rowSize,
                                  double thumbnailSizeMultiplier,
                                  MapWorkspace mapWorkspace) {
        super(observableList, rowSize, thumbnailSizeMultiplier);

        myMapWorkspace = mapWorkspace;
    }

    public LevelUpdatableDisplay (LevelSidebar l, Controller c,
                                  String partType,
                                  int rowSize,
                                  double thumbnailSizeMultiplier, MapWorkspace mapWorkspace) {

        super(c, partType, rowSize, thumbnailSizeMultiplier);
        sidebar = l;
        myMapWorkspace = mapWorkspace;
        myController = c;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);        // create a new map
        try {
            sidebar.setKey(object.getKey());
            myMapWorkspace.getChildren().add(new ImageView(myController.getImageForKey(object
                                                     .getKey())));

        }
        catch (NoImageFoundException e) {
            e.printStackTrace();
        }
    }

}
