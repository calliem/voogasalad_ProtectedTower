package engine.element;

import java.util.Map;
import engine.element.sprites.GridCell;


public class GameMap extends GameElement {

    private GridCell[][] myMap;

    public GameMap (Map<String, Object> params) {
        super.setParameterMap(params);
        loadMap();
    }

    private void loadMap () {
        String[][] mapLayout = (String[][]) super.getParameter("MapLayout");
        Double tileSize = (Double) super.getParameter("TileSize");

        GridCell[][] map = new GridCell[mapLayout.length][mapLayout[0].length];

        // for (int[] key : mapLayout.keySet()) {
        // GridCell cell = factory.getGridCell(mapLayout.get(key));
        // int row = key[0];
        // int col = key[1];
        // cell.setLocation(col * tileSize, row * tileSize);
        // cell.setCenter((col + 1 / 2) * tileSize, (row + 1 / 2) * tileSize);
        // map[row][col] = cell;
        // }
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
