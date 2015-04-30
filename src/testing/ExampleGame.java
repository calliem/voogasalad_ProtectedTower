package testing;

import java.util.HashMap;
import java.util.Map;
import authoringEnvironment.InstanceManager;


public class ExampleGame {

    public static final Map<String, Object> GAME = generateExampleGame();

    public static Map<String, Object> generateExampleGame () {
        Map<String, Object> game = new HashMap<String, Object>();

        Integer lives = 20;

        game.put(InstanceManager.NAME_KEY, "DesktopTestGame");
        game.put(InstanceManager.PART_TYPE_KEY, "Game");
        game.put(InstanceManager.PART_KEY_KEY, "DesktopTestGame_Part0.Game");
        game.put("lives", lives);

        return game;
    }

}
