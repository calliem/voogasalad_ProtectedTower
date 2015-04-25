package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import authoringEnvironment.GameCreator;
import authoringEnvironment.InstanceManager;
import engine.element.Game;


/**
 * This class contains the main game loop which runs the game and updates the
 * Scene. The constructor is given an ObservableList of nodes in the scene, as
 * well as a list of the towers, enemies and map created by the authoring
 * environment. A new version of this class must be created by the player
 * whenever a new game is loaded. The player calls this class when it loads a
 * new game, and this class then runs the game.
 * 
 * @author Qian Wang
 */

public class GameController {
    private static final String PARAMETER_PARTTYPE = "PartType";
    private static final String PARAMETER_GUID = "PartKey";
    /**
     * Holds a set of part names to load
     */
    private static final String[] PART_NAMES = new String[] { "Tower", "Enemy", "Projectile",
                                                             "GridCell", "GameMap", "Round",
                                                             "Wave", "Game", "Layout", "Level" };
    /**
     * Holds a subset of part names to give to the game element factory
     */
    private static final String[] FACTORY_PART_NAMES = new String[] { "Tower", "Enemy",
                                                                     "Projectile", "GridCell",
                                                                     "GameMap", "Round", "Wave" };

    /**
     * Holds an instance of an entire game
     */
    private Game myGame;
    private TowerManager myTowerManager;

    /**
     * Creates a new instance of a game represented by the XML files at a given file location.
     * 
     * @param filepath String to the main directory holding the game
     * @param nodes List<Node> reference to that used in the Player class to display the game
     * @throws InsufficientParametersException when filepath does not point to a well defined game
     *         file
     */
    public GameController (String filepath, List<Node> nodes)
        throws InsufficientParametersException {
        myGame = this.loadGame(filepath, nodes);
        myTowerManager = new TowerManager();
    }

    /**
     * Given a location of a game file, the {@link GameCreator#loadGame(String)} method if called,
     * which generates a map of objects names to the
     * parameters which those objects should contain. Those objects are then
     * instantiated and their parameter lists are set.
     * 
     * @param filepath String of location of the game file
     * @param nodes List<Node> list of javafx nodes that the game can update
     * @throws InsufficientParametersException when multiple games/layouts are created, or when no
     *         game elements are specified
     */
    private Game loadGame (String filepath, List<Node> nodes)
                                                             throws InsufficientParametersException {
        Map<String, Map<String, Map<String, Object>>> myObjects = new HashMap<>();
        for (String partName : PART_NAMES) {
            myObjects.put(partName, new HashMap<>());
        }

        // Get list of parameters maps for all objects
        // TODO change to collection or set
        Map<String, Map<String, Object>> allDataObjects = InstanceManager.loadGameData(filepath);

        // Organize parameters maps
        for (String key : allDataObjects.keySet()) {
            Map<String, Object> obj = allDataObjects.get(key);
            String partType = (String) obj.get(PARAMETER_PARTTYPE);

            // System.out.println(obj);
            myObjects.get(partType).put((String) obj.get(PARAMETER_GUID), obj);
            System.out.println((String) obj.get(PARAMETER_GUID));
        }

        return initializeGame(nodes, myObjects);
    }

    /**
     * Creates a new Game object given a mapping of all possible objects that can exist in that
     * game. Error checking is done to make sure only one game and layout are used.
     * 
     * @param nodes List<Node> list of javafx nodes that the game can update
     * @param myObjects Map<String, Map<String, Map<String, Object>>> representing mapping of part
     *        name to the specific objects of that type
     * @return Game object which has been instantiated with given objects
     * @throws InsufficientParametersException if inputted objects do not fulfill game requirements
     */
    private Game initializeGame (List<Node> nodes,
                                 Map<String, Map<String, Map<String, Object>>> myObjects)
                                                                                         throws InsufficientParametersException {
        // store game parameters
        Game myGame = new Game(nodes);

        // TODO test for errors for 0 data files, or too many
        if (myObjects.get("Game").size() != 1) {
            throw new InsufficientParametersException("Zero or multiple game data files created");
        }
        else {
            for (Map<String, Object> map : myObjects.get("Game").values()) {
                myGame.setParameterMap(map);
            }
        }
        if (myObjects.get("Layout").size() != 1) {
            throw new InsufficientParametersException("Zero or multiple game layouts created");
        }
        else {
            for (Map<String, Object> map : myObjects.get("Layout").values()) {
                myGame.addLayoutParameters(map);
            }
        }
        if (myObjects.get("Level").size() < 1) {
            throw new InsufficientParametersException("No game levels created");
        }
        else {
            myGame.addLevels(myObjects.get("Level"));
        }

        // Send right sets of objects to the right objects
        for (String partName : FACTORY_PART_NAMES) {
            myGame.addGameElement(partName, myObjects.get(partName));
        }
        
        myTowerManager.add(myObjects.get("Tower"));

        return myGame;
    }

    /**
     * Called by the player to tell engine about keypressed
     * 
     * @param key KeyEvent object
     */
    public void handleKeyInput (KeyEvent key) {

    }

    public static void main (String[] args) throws InsufficientParametersException {
        GameController test =
                new GameController(
                                   "src\\exampleUserData\\TestingManagerGame\\TestingManagerGame.gamefile",
                                   new ArrayList<Node>());
    }
}
