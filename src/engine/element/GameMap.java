package engine.element;

import java.util.Map;
import annotations.parameter;
import engine.element.sprites.GridCell;
import engine.element.sprites.GridCellFactory;


public class GameMap extends GameElement {
    @parameter(settable=true, playerDisplay=false)
    private GridCell[][] myMap;
    @parameter(settable=true, playerDisplay=false)
    private int rows = 10;
    @parameter(settable=true, playerDisplay=false)
    private int columns = 10;
    @parameter(settable=true, playerDisplay=false)
    private int tileSize =  5;
    public GameMap (Map<String, Object> params) {
        super.setParameterMap(params);
        loadMap();
    }

    private void loadMap () {
        Map<int[], String> mapLayout = (Map<int[], String>) super.getParameter("MapLayout");
        int rows = (Integer) super.getParameter("Rows");
        int cols = (Integer) super.getParameter("Columns");
        Double tileSize = (Double) super.getParameter("TileSize");
        GridCell[][] map = new GridCell[rows][cols];
        GridCellFactory factory = new GridCellFactory();
        for (int[] key : mapLayout.keySet()) {
            GridCell cell = factory.getGridCell(mapLayout.get(key));
            int row = key[0];
            int col = key[1];
            cell.setLocation(col * tileSize, row * tileSize);
            cell.setCenter((col + 1 / 2) * tileSize, (row + 1 / 2) * tileSize);
            map[row][col] = cell;
        }
        myMap = map;
    }

    public GridCell[][] getMap () {
        return myMap;
    }

    public double getCoordinateHeight () {
        return (double) super.getParameter("Rows") * (double) super.getParameter("TileSize");
    }

    public double getCoordinateWidth () {
        return (double) super.getParameter("Columns") * (double) super.getParameter("TileSize");
    }
}
