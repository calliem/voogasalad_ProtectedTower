package authoringEnvironment.objects;

import java.util.List;
import javafx.scene.layout.StackPane;
import authoringEnvironment.map.MapWorkspace;


public class PathUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;

    public PathUpdatableDisplay (List<GameObject> list, int rowSize, int thumbnailSize, MapWorkspace mapWorkspace) {
        super(list, rowSize, thumbnailSize);
        myMapWorkspace = mapWorkspace;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);        // create a new map
        myMapWorkspace.updateWithNewPath(object);

    }

}
