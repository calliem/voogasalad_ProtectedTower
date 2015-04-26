package authoringEnvironment.objects;

import java.util.List;
import javafx.scene.layout.StackPane;
import authoringEnvironment.Controller;
import authoringEnvironment.map.MapWorkspace;


public class LevelUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;

    public LevelUpdatableDisplay (Controller c, String partName, int rowSize, MapWorkspace workspace) {
        super(c, partName, rowSize);
        myMapWorkspace = workspace;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);        // create a new map
        myMapWorkspace.updateWithNewMap(object, null);
    }

}
