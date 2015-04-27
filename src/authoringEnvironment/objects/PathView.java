package authoringEnvironment.objects;

import java.util.ArrayList;


/**
 * Class containing all properties of paths. Paths are set in the map editor by
 * selecting tiles in specific orders. Each path and its properties (including
 * order of tiles) are stored within the PathView object.
 * 
 * @author Callie Mao
 *
 */
public class PathView {

    private ArrayList<Coordinate> myCoordinatePath;

    public PathView () {
        myCoordinatePath = new ArrayList<Coordinate>();
    }

}
