package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoringEnvironment.InstanceManager;

public class ExampleGame {
    
    public static final Map<String,Map<String, Object>> GAME = generateExampleGame();

    private static Map<String, Map<String, Object>> generateExampleGame () {
        Map<String, Object> game = new HashMap<String, Object>();
        Map<String,Map<String,Object>> part = new HashMap<String,Map<String,Object>>();
        
        Integer lives = 20;
        
        game.put(InstanceManager.NAME_KEY, "DesktopTestGame");
        game.put(InstanceManager.PART_TYPE_KEY, "Game");
        game.put(InstanceManager.PART_KEY_KEY, "DesktopTestGame_Part0.Game");
        game.put("lives", lives);
        
        part.put("DesktopTestGame_Part0.Game", game);

        return part;
    }

}
