package authoringEnvironment.objects;

import java.util.List;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import authoringEnvironment.Controller;
import authoringEnvironment.map.MapWorkspace;


public class TileUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;

    public TileUpdatableDisplay (List<GameObject> list,
                                 int rowSize,
                                 int thumbnailSize,
                                 MapWorkspace mapWorkspace) {
        super(list, rowSize, thumbnailSize);
        myMapWorkspace = mapWorkspace;
    }

    public TileUpdatableDisplay (Controller c,
                                 String partType,
                                 int rowSize,
                                 double thumbnailSizeMultiplier, MapWorkspace mapWorkspace) {
        super(c, partType, rowSize, thumbnailSizeMultiplier);
        myMapWorkspace = mapWorkspace;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);
        //mySidebar.changeMap((TileMap) object);
         myMapWorkspace.setActiveColor(Color.BLUE); //TODO: test

        // mySidebar.getMapWorkspace().updateWithNewMap(object);
        // mySidebar.setMapNameTextField(object.getName());
        // mySidebar.setPaths
    }

}
