package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.Map;
import util.reflection.Reflection;


/**
 * Factory for producing grid cells
 * 
 * @author Qian Wang
 *
 */
public class GridCellFactory extends SpriteFactory {

    private Map<String, Map<String, Object>> myGridCells;
    private final static String MY_CLASS_NAME = "engine.element.sprites.GridCell";

    public GridCellFactory () {
        super(MY_CLASS_NAME);
    }

    /**
     * Given a GUID, returns the grid cell object with a prefilled parameters map and values that
     * it represents
     * 
     * @param guid String of GUID identifying the object
     * @return GridCell object
     */
    public GridCell getGridCell (String guid) {
        if (!myGridCells.containsKey(guid)) { throw new InvalidParameterException(guid +
                                                                                  " is an undefined grid cell"); }

        GridCell gridcell = (GridCell) Reflection.createInstance(MY_CLASS_NAME);
        gridcell.setParameterMap(myGridCells.get(guid));

        return gridcell;
    }

}
