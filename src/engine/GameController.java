package engine;

import java.util.List;
import java.util.Map;
<<<<<<< HEAD

=======
>>>>>>> 58685c6ca7b085f1dac14413b3bdfecfbf4ca9c7
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import authoringEnvironment.GameManager;
import engine.element.Game;


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

    public GameController () {
        myGame = new Game();
    }

    /**
     * Given a location of a game file, the {@link GameManager#loadGame(String)} method if called,
     * which generates a map of objects names to the parameters which those objects should contain.
     * Those objects are then instantiated and their parameter lists are set.
     * 
     * @param filepath String of location of the game file
     * @param engineRoot 
     */

    public void loadGame (String filepath) {
        List<Map<String, Object>> allDataObjects = GameManager.loadGame(filepath);

        /*
         * for (String s : allDataObjects.keySet()) {
         * Sprite currentObject = (Sprite) Reflection.createInstance(s);
         * currentObject.setParameterMap(allDataObjects.get(s));
         * // allObjects.add(currentObject);
         * // TODO need way to load objects into correct classes, like Layout and Wave
         * }
         */
    }

    /**
     * Called by the player to give the engine a group to add sprite nodes to
     * 
     * @param group Javafx Group object
     */
    public void setGroup (Group group) {
        myGroup = group;
    }

    /**
     * Called by the player to tell engine about keypressed
     * 
     * @param key KeyEvent object
     */
    public void handleKeyInput (KeyEvent key) {

    }

}
