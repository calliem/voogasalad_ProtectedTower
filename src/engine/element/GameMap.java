package engine.element;

import annotations.parameter;
import engine.element.sprites.GridCell;


public class GameMap {
    @parameter(settable = true, playerDisplay = false)
    private GridCell[][] myMap;
    @parameter(settable = true, playerDisplay = false)
    private int rows = 10;
    @parameter(settable = true, playerDisplay = false)
    private int columns = 10;
    @parameter(settable = true, playerDisplay = false)
    private int tileSize = 5;

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
