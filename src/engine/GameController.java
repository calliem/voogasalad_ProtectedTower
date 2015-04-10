package engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import util.reflection.Reflection;
import authoringEnvironment.GameManager;
import authoringEnvironment.InstanceManager;
import engine.element.Game;
import engine.element.sprites.Enemy;
import engine.element.sprites.Projectile;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;


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
    private static final String PARAMETER_GUID = "GUID";
    /**
     * Holds an instance of an entire game
     */
    Game myGame;
    /**
     * Holds a map of a part name to the package to use to reflect
     */
    Map<String, String> myPartTypeToPackage = new HashMap<>();

    public GameController (String filepath) {
        myGame = new Game();
        loadGame(filepath, myGame);
        fillPackageMap();
    }

    // TODO replace this with loading from data file
    private void fillPackageMap () {
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
    public void loadGame (String filepath, Game game) {
        // Collection<Tower> towerObjects = new HashSet<Tower>();
        // Collection<Enemy> enemyObjects = new HashSet<Enemy>();
        // Collection<Projectile> projectileObjects = new HashSet<Projectile>();
        Map<String, Map<String, Map<String, Object>>> myObjects = new HashMap<>();
        for (String s : myPartTypeToPackage.keySet()) {
            myObjects.put(s, new HashMap<>());
        }

        // Get list of parameters maps for all objects
        // TODO change to collection or set
        List<Map<String, Object>> allDataObjects = GameManager.loadGame(filepath);

        // Organize parameters maps
        for (Map<String, Object> obj : allDataObjects) {
            String partType = (String) obj.get(InstanceManager.partTypeKey);
            // String packageLocation = myPartTypeToPackage.get(partType);
            // Sprite currentObject = (Sprite) Reflection.createInstance(packageLocation);
            // currentObject.setParameterMap(obj);
            myObjects.get(partType).put((String) obj.get(PARAMETER_GUID), obj);
        }

        // Send right sets of objects to the right objects
        myGame.addTowers(myObjects.get("Tower"));
        myGame.addTowers(myObjects.get("Enemy"));
        myGame.addTowers(myObjects.get("Projectile"));
        myGame.addTowers(myObjects.get("GridCell"));
    }

    // Will handle hotkeys
    public void handleKeyInput (KeyEvent k) {

    }

}
