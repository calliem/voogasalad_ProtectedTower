package authoringEnvironment.objects;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import authoringEnvironment.Controller;
import authoringEnvironment.map.MapSidebar;
import authoringEnvironment.map.MapWorkspace;

public class MapUpdatableDisplay extends UpdatableDisplay {
    
    private MapSidebar mySidebar;
    
    public MapUpdatableDisplay (Controller c, String partName, int rowSize, MapSidebar sidebar) {
        super(c, partName, rowSize);
        mySidebar = sidebar;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);
        mySidebar.changeMap((TileMap) object);
        
        //mySidebar.getMapWorkspace().updateWithNewMap(object);
        //mySidebar.setMapNameTextField(object.getName());
        //mySidebar.setPaths
    }

}
