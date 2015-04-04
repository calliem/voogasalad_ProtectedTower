package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import util.reflection.Reflection;
import authoringEnvironment.GameManager;
import engine.sprites.GridCell;
import engine.sprites.Sprite;


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
     * Holds a set of all objects instantiated from data files
     */
    Set<Sprite> allObjects;

    public GameController () throws InsufficientParametersException {
        allObjects = new HashSet<>();
    }

    // still need this?
    // public GameController (ObservableList<Node> sceneContents, XStream inputStream)
    // throws InsufficientParametersException {
    //
    // }

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
            Sprite currentObject = (Sprite) Reflection.createInstance(s);
            currentObject.setParameterMap(allDataObjects.get(s));
            allObjects.add(currentObject);
            // TODO need way to load objects into correct classes, like Layout and Wave
        }
    }

    public ArrayList<Node> displayMap (Iterable<GridCell> map) {
        return null;
    }

    // Will handle hotkeys
    public void handleKeyInput (KeyEvent k) {

    }

}
