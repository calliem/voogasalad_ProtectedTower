package engine;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import util.reflection.Reflection;
import authoringEnvironment.GameManager;
import authoringEnvironment.InstanceManager;
import engine.element.Game;
import engine.element.sprites.Sprite;


/**
 * This class contains the main game loop which runs the game and updates the Scene. The constructor
 * is given an ObservableList of nodes in the scene, as well as a list of the towers, enemies and
 * map created by the authoring environment. A new version of this class must be created by the
 * player whenever a new game is loaded. The player calls this class when it loads a new game, and
 * this class then runs the game.
 * 
 * @author Qian Wang
 */

public class GameController {
    /**
     * Holds an instance of an entire game
     */
    Game myGame;
    /**
     * Holds a map of a part name to the package to use to reflect
     */
    Map<String, String> myPartTypeToPackage = new HashMap<>();

    public GameController () {
        myGame = new Game();
        myPartTypeToPackage.put("Tower", "engine.element.sprites.Tower");
        myPartTypeToPackage.put("Enemy", "engine.element.sprites.Enemy");
        myPartTypeToPackage.put("Projectile", "engine.element.sprites.Projectile");
        myPartTypeToPackage.put("GridCell", "engine.element.sprites.GridCell");
        myPartTypeToPackage.put("Game", "engine.element.Game");
        myPartTypeToPackage.put("Level", "engine.element.Level");
        myPartTypeToPackage.put("Round", "engine.element.Round");
        myPartTypeToPackage.put("Wave", "engine.element.Wave");
        myPartTypeToPackage.put("Layout", "engine.element.Layout");
    }

    /**
     * Given a location of a game file, the {@link GameManager#loadGame(String)} method if called,
     * which generates a map of objects names to the parameters which those objects should contain.
     * Those objects are then instantiated and their parameter lists are set.
     * 
     * @param filepath String of location of the game file
     */
    public void loadGame (String filepath) {
        Map<String, Map<String, Object>> allDataObjects = GameManager.loadGame(filepath);

        for (String s : allDataObjects.keySet()) {
//            String partType = part.get(InstanceManager.partTypeKey);
            String packageLocation = myPartTypeToPackage.get(s);
            Sprite currentObject = (Sprite) Reflection.createInstance(s);
            currentObject.setParameterMap(allDataObjects.get(s));
            // allObjects.add(currentObject);
            // TODO need way to load objects into correct classes, like Layout and Wave
        }
    }

    // Will handle hotkeys
    public void handleKeyInput (KeyEvent k) {

    }

}
