package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoringEnvironment.InstanceManager;


public class ExampleGameMap {

    public static Map<String, Map<String, Object>> generateExampleMap () {
        Map<String, Object> gameMap = new HashMap<String, Object>();
        Map<String, Map<String, Object>> part = new HashMap<String, Map<String, Object>>();

        Integer rows = 20;
        Integer columns = 30;
        Integer tileSize = 5;
        String[][] myTileNames = new String[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (String cell : myTileNames[row]) {
                if (row < rows / 2) {
                    cell = "Part0.GridCell";
                }
                else {
                    cell = "Part1.GridCell";
                }
            }
        }

        gameMap.put(InstanceManager.NAME_KEY, "DesktopTestGameMap");
        gameMap.put(InstanceManager.PART_TYPE_KEY, "GameMap");
        gameMap.put(InstanceManager.PART_KEY_KEY, "DesktopTestGameMap_Part0.GameMap");
        gameMap.put("Rows", rows);
        gameMap.put("Columns", columns);
        gameMap.put("TileSize", tileSize);
        gameMap.put("TileNames", myTileNames);
        gameMap.put("Image", "images/Capture.PNG");

        gameMap.put("Tags", "none");
        gameMap.put("BoundingHeight", 10.0);
        gameMap.put("BoundingWidth", 10.0);
        List<String> paths = new ArrayList<String>();
        paths.add("ExampleGame_Path0.Path");
        gameMap.put("Paths", paths);

        part.put("DesktopTestGameMap_Part0.GameMap", gameMap);
        return part;
    }
    
    public static Map<String, Map<String, Object>> generateExampleMap2 () {
        Map<String, Object> gameMap = new HashMap<String, Object>();
        Map<String, Map<String, Object>> part = new HashMap<String, Map<String, Object>>();

        Integer rows = 20;
        Integer columns = 30;
        Integer tileSize = 5;
        String[][] myTileNames = new String[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (String cell : myTileNames[row]) {
                if (row < rows / 2) {
                    cell = "Part0.GridCell";
                }
                else {
                    cell = "Part1.GridCell";
                }
            }
        }

        gameMap.put(InstanceManager.NAME_KEY, "DesktopTestGameMap");
        gameMap.put(InstanceManager.PART_TYPE_KEY, "GameMap");
        gameMap.put(InstanceManager.PART_KEY_KEY, "DesktopTestGameMap_Part0.GameMap");
        gameMap.put("Rows", rows);
        gameMap.put("Columns", columns);
        gameMap.put("TileSize", tileSize);
        gameMap.put("TileNames", myTileNames);
        gameMap.put("Image", "images/Death_Valley.png");

        gameMap.put("Tags", "none");
        gameMap.put("BoundingHeight", 10.0);
        gameMap.put("BoundingWidth", 10.0);
        List<String> paths = new ArrayList<String>();
        paths.add("ExampleGame_Path0.Path");
        gameMap.put("Paths", paths);

        part.put("DesktopTestGameMap_Part0.GameMap", gameMap);
        return part;
    }
}
