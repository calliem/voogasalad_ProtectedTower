package engine.element;

import annotations.parameter;
import engine.element.sprites.GridCell;


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

    // public void loadMap (GameElementFactory factory) {
    // String[][] mapLayout = (String[][]) super.getParameter("MapLayout");
    // Double tileSize = (Double) super.getParameter("TileSize");
    //
    // GridCell[][] map = new GridCell[mapLayout.length][mapLayout[0].length];
    //
    // for (int i = 0; i < mapLayout.length; i++) {
    // for (int j = 0; j < mapLayout[0].length; j++) {
    // map[i][j] = (GridCell) factory.getGameElement("GridCell", mapLayout[i][j]);
    // }
    // }
    // myMap = map;
    // }

    public GridCell[][] getMap () {
        return myMap;
    }

    public double getCoordinateHeight () {
        return rows * tileSize;
    }

    public double getCoordinateWidth () {
        return columns * tileSize;
    }
}
