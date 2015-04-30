package authoringEnvironment.objects;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import authoringEnvironment.Controller;
import authoringEnvironment.map.MapWorkspace;


public class LevelUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;

    public LevelUpdatableDisplay (ObservableList<GameObject> observableList, int rowSize, double thumbnailSizeMultiplier, MapWorkspace mapWorkspace) {
        super(observableList, rowSize, thumbnailSizeMultiplier);
        myMapWorkspace = mapWorkspace;
        // TODO Auto-generated constructor stub
    }
    
    public LevelUpdatableDisplay (Controller c,
                                 String partType,
                                 int rowSize,
                                 double thumbnailSizeMultiplier, MapWorkspace mapWorkspace) {
        super(c, partType, rowSize, thumbnailSizeMultiplier);
        myMapWorkspace = mapWorkspace;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);        // create a new map
        myMapWorkspace.updateWithNewMap(object);

    }

}
