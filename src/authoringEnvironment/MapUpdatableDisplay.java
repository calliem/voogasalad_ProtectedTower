package authoringEnvironment;

import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import authoringEnvironment.map.MapSidebar;
import authoringEnvironment.map.MapWorkspace;
import authoringEnvironment.objects.GameObject;
import authoringEnvironment.objects.TileMap;

public class MapUpdatableDisplay extends UpdatableDisplay {
    
    private MapSidebar mySidebar;
    
    public MapUpdatableDisplay (List<GameObject> list, int rowSize, MapSidebar sidebar) {
        super(list, rowSize);
        mySidebar = sidebar;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);
        mySidebar.changeMap((TileMap) object);
        System.out.println("change map");
        
        //mySidebar.getMapWorkspace().updateWithNewMap(object);
        //mySidebar.setMapNameTextField(object.getName());
        //mySidebar.setPaths
    }

}
