package engine.element;

import annotations.parameter;
import engine.element.sprites.GridCell;
import engine.factories.GameElementFactory;


/**
 * This class represents the map for a game, containing cells which specify certain tags to
 * determine if towers can be placed on them, if enemies can travel over, etc.
 * 
 * @author Qian Wang
 *
 */
public class GameMap {

    @parameter(settable = true, playerDisplay = false, defaultValue = "20")
    private String[][] myTileNames;
    @parameter(settable = true, playerDisplay = false, defaultValue = "20")
    private Integer rows;
    @parameter(settable = true, playerDisplay = false, defaultValue = "30")
    private Integer columns;
    @parameter(settable = true, playerDisplay = false, defaultValue = "5")
    private Integer tileSize;

    /**
     * Holds the actual grid of cells, showing which cells have which tags
     */
    private GridCell[][] myMap;

    public GameMap () {

    }

    /**
     * Creates an instance of the map made of GridCells and stores it as an instance variable
     * 
     * @param factory GameElementFactory which can be used to generate the correct GridCell objects
     */
    public void loadMap (GameElementFactory factory) {
        myMap = new GridCell[myTileNames.length][myTileNames[0].length];
        for (int i = 0; i < myTileNames.length; i++) {
            for (int j = 0; j < myTileNames[i].length; j++) {
                myMap[i][j] = (GridCell) factory.getGameElement("GridCell", myTileNames[i][j]);
            }
        }
    }

    public GridCell[][] getMap () {
        return myMap;
    }

    /**
     * @return total height of the map in pixels
     */
    public double getCoordinateHeight () {
        return rows * tileSize;
    }

    /**
     * @return total width of the map in pixels
     */
    public double getCoordinateWidth () {
        return columns * tileSize;
    }
    
    public int[] getRowColAtCoordinates(double x, double y){    	
    	int[] rowCol = { (int) (rows*y/getCoordinateHeight()), (int) (columns*x/getCoordinateWidth()) };
    	return rowCol;
    }
}
