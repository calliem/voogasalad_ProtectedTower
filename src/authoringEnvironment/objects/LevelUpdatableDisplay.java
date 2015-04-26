package authoringEnvironment.objects;

import java.util.List;
import javafx.scene.layout.StackPane;
import authoringEnvironment.map.MapWorkspace;


public class LevelUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;

    public LevelUpdatableDisplay (List<GameObject> list, int rowSize, MapWorkspace mapWorkspace) {
        super(list, rowSize);
        myMapWorkspace = mapWorkspace;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);        // create a new map
        myMapWorkspace.updateWithNewMap(object);

    }

}
