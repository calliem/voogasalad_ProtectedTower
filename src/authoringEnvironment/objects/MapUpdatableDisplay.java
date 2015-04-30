package authoringEnvironment.objects;

import java.util.List;
import javafx.scene.layout.StackPane;
import authoringEnvironment.map.MapSidebar;


public class MapUpdatableDisplay extends UpdatableDisplay {

    private MapSidebar mySidebar;

    public MapUpdatableDisplay (List<GameObject> list, int rowSize, double thumbnailSizeMultiplier, MapSidebar sidebar) {
        super(list, rowSize, thumbnailSizeMultiplier);
        mySidebar = sidebar;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);
        mySidebar.changeMap((TileMap) object);
        mySidebar.updatePathDisplay((TileMap) object);
    }

}
