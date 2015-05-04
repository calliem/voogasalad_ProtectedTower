// This entire file is part of my masterpiece.
// Callie Mao

package authoringEnvironment.objects;

import java.util.List;
import javafx.scene.layout.StackPane;
import authoringEnvironment.Controller;
import authoringEnvironment.pathing.PathView;
import authoringEnvironment.map.MapWorkspace;


/**
 * Template Method Design Pattern subclass that specifies actions of the superclass's methods (and
 * potentially other menthods in later extensions as well). This class is an example of how the
 * UpdatableDisplay hierarchy would be utilized.
 * 
 * @author Callie Mao
 *
 */



public class PathUpdatableDisplay extends UpdatableDisplay {

    private MapWorkspace myMapWorkspace;

    /**
     * Creates an UpdatableDisplay of game objects from the given list. Keeps track of the current
     * map workspace.
     * 
     * @param list
     * @param rowSize
     * @param thumbnailSizeMultiplier
     * @param mapWorkspace
     */
    public PathUpdatableDisplay (List<GameObject> list,
                                 int rowSize,
                                 double thumbnailSizeMultiplier,
                                 MapWorkspace mapWorkspace) {
        super(list, rowSize, thumbnailSizeMultiplier);
        myMapWorkspace = mapWorkspace;
    }

    /**
     * Creates an UpdatableDisplay of Simple Objects based on the partType from the controller.
     * Keeps track of the
     * current map workspace.
     * 
     * @param c
     * @param partType
     * @param rowSize
     * @param thumbnailSizeMultiplier
     * @param mapWorkspace
     */
    public PathUpdatableDisplay (Controller c,
                                 String partType,
                                 int rowSize,
                                 double thumbnailSizeMultiplier, MapWorkspace mapWorkspace) {
        super(c, partType, rowSize, thumbnailSizeMultiplier);
        myMapWorkspace = mapWorkspace;
    }

    /**
     * Update map workspace with selected path upon object click. This method is called within a
     * loop through objects within a GameObjects list in the superclass.
     */
    @Override
    protected void objectClicked (GameObject object, StackPane objectView) {
        super.objectClicked(object, objectView);
        myMapWorkspace.updateWithNewPath((PathView) object);
    }

}
