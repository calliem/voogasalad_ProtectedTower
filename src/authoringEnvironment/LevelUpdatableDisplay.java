package authoringEnvironment;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.GameObject;

public class LevelUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;

    public LevelUpdatableDisplay (List<GameObject> list, int rowSize, MapWorkspace mapWorkspace) {
        super(list, rowSize);
        myMapWorkspace = mapWorkspace;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);        //create a new map
        myMapWorkspace.updateWithNewMap(object, null);
        
        
        
    }

}
