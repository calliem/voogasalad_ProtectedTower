package authoringEnvironment.objects;

import java.util.List;
import javafx.scene.layout.StackPane;
import authoringEnvironment.Controller;
import authoringEnvironment.pathing.PathView;

import authoringEnvironment.map.MapWorkspace;


public class PathUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;

    public PathUpdatableDisplay (List<GameObject> list,
                                 int rowSize,
                                 double thumbnailSizeMultiplier,
                                 MapWorkspace mapWorkspace) {
        super(list, rowSize, thumbnailSizeMultiplier);
        myMapWorkspace = mapWorkspace;
    }

    public PathUpdatableDisplay (Controller c,
                                 String partType,
                                 int rowSize,
                                 double thumbnailSizeMultiplier, MapWorkspace mapWorkspace) {
        super(c, partType, rowSize, thumbnailSizeMultiplier);
        myMapWorkspace = mapWorkspace;
    }

    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);      
        System.out.println(object);
        myMapWorkspace.updateWithNewPath((PathView) object);
    //    object.getRoot().setVisible(true);

        
    }

}
