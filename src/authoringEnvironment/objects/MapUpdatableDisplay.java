// This entire file is part of my masterpiece.
// Callie Mao

package authoringEnvironment.objects;

import java.util.List;
import javafx.scene.layout.StackPane;
import authoringEnvironment.Controller;
import authoringEnvironment.map.MapSidebar;
import authoringEnvironment.map.MapWorkspace;

/**
 * Another example of UpdatableDisplay subclass extension
 * @author Callie Mao
 *
 */

public class MapUpdatableDisplay extends UpdatableDisplay {

    private MapSidebar mySidebar;

    public MapUpdatableDisplay (List<GameObject> list, int rowSize, double thumbnailSizeMultiplier, MapSidebar sidebar) {
        super(list, rowSize, thumbnailSizeMultiplier);
        mySidebar = sidebar;
    }
    
    public MapUpdatableDisplay (Controller c,
                                 String partType,
                                 int rowSize,
                                 double thumbnailSizeMultiplier, MapSidebar sidebar) {
        super(c, partType, rowSize, thumbnailSizeMultiplier);
        mySidebar = sidebar;
    }

    @Override
    protected void objectSelected (GameObject object, StackPane objectView) {
        super.objectSelected(object, objectView);
        mySidebar.changeMap((TileMap) object);
        mySidebar.updatePathDisplay((TileMap) object);
    }

}
