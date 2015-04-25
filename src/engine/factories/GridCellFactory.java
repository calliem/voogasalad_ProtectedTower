package engine.factories;

import engine.element.sprites.GridCell;

/**
 * Factory for producing grid cells
 * 
 * @author Qian Wang
 *
 */
public class GridCellFactory extends GameElementFactory {
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
        return (GridCell) super.getGameElement(guid);
    }

}