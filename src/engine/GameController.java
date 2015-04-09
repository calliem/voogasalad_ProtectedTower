package engine;

import java.util.Map;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import util.reflection.Reflection;
import authoringEnvironment.GameManager;
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
    private Game myGame;
    /**
     * Javafx object so that new nodes can be added for the player to display
     */
    private Group myGroup;
    private Bank myBank;
    private int myPoints;

    public GameController () {
        myGame = new Game();
        myBank = new Bank();
        myPoints = 0;
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
            Sprite currentObject = (Sprite) Reflection.createInstance(s);
            currentObject.setParameterMap(allDataObjects.get(s));
            // allObjects.add(currentObject);
            // TODO need way to load objects into correct classes, like Layout and Wave
        }
    }

    public void setGroup (Group g) {
        myGroup = g;
    }

    // Will handle hotkeys
    public void handleKeyInput (KeyEvent k) {

    }

}
