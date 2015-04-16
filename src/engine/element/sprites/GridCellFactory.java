package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;


/**
 * Factory for producing grid cells
 * 
 * @author Qian Wang
 *
 */
public class GridCellFactory {

    private Map<String, Map<String, Object>> myGridCells;
    private final static String MY_CLASS_NAME = "engine.element.sprites.GridCell";

    public GridCellFactory () {
        myGridCells = new HashMap<>();
    }

    /**
     * Adds new grid cells to the list of all possible grid cells, can be called with a map of
     * grid cells GUID to the parameters map of that grid cells, or also with a single GUID and a
     * single parameters map.
     * 
     * @param allSprites Map<String, Map<String, Object>> object
     */
    public void add (Map<String, Map<String, Object>> allSprites) {
        // TODO refactor into superclass for factories
        allSprites.keySet().forEach(t -> this.add(t, allSprites.get(t)));
    }

    /**
     * @see GridCellFactory#add(Map)
     * 
     * @param gridcellID String of the GUID of the projectile
     * @param gridcellProperties the properties Map<String, Object> object of the projectile
     */
    public void add (String gridcellID, Map<String, Object> gridcellProperties) {
        myGridCells.put(gridcellID, gridcellProperties);
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
