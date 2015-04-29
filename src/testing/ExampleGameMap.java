package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoringEnvironment.InstanceManager;


public class ExampleGameMap {

    public static final Map<String,Map<String, Object>> MAP = generateExampleMap();

    private static Map<String, Map<String, Object>> generateExampleMap () {
        Map<String, Object> gameMap = new HashMap<String, Object>();
        Map<String,Map<String,Object>> part = new HashMap<String,Map<String,Object>>();
        
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
        gameMap.put("rows", rows);
        gameMap.put("columns", columns);
        gameMap.put("tileSize", tileSize);
        gameMap.put("myTileNames", myTileNames);
        
        part.put("DesktopTestGameMap_Part0.GameMap", gameMap);
        return part;
    }
}
