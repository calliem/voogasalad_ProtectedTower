// This entire file is part of my masterpiece.
// Qian Wang

package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import util.engine.PropertiesFileLoader;
import authoringEnvironment.GameCreator;
import authoringEnvironment.InstanceManager;
import engine.element.Game;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;


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
    private static final Set<String> PART_NAMES = PropertiesFileLoader
            .loadToSet("src/resources/partNamesToLoad.properties");
    /**
     * Holds a subset of part names to give to the game element factory
     */
    private static final Set<String> FACTORY_PART_NAMES = PropertiesFileLoader
            .loadToSet("src/resources/partNamesToFactory.properties");

    /**
     * Holds an instance of an entire game
     */
    private Game myGame;
    private TowerManager myTowerManager;

    /**
     * Creates a new instance of a game represented by the XML files at a given file location.
     * 
     * @param filepath String to the main directory holding the game
     * @param nodes List<Sprite> reference to that used in the Player class to display the game
     * @param possibleTower List<Tower> reference that is used in Player class to display all towers
     *        to place
     * @throws InsufficientParametersException when filepath does not point to a well defined game
     *         file
     */
    public GameController (String filepath,
                           List<Sprite> nodes,
                           List<Tower> possibleTowers)
        throws InsufficientParametersException {
        myTowerManager = new TowerManager();
        myGame = this.loadGame(filepath, nodes, possibleTowers);
        myGame.updateBackgroundTest("DesktopTestGameMap_Part0.GameMap");
    }

    /**
     * Given a location of a game file, the {@link GameCreator#loadGame(String)} method if called,
     * which generates a map of objects names to the
     * parameters which those objects should contain. Those objects are then
     * instantiated and their parameter lists are set.
     * 
     * @param filepath String of location of the game file
     * @param nodes List<Sprite> list of sprites that the game can update
     * @param possibleTowers
     * @param background
     * @throws InsufficientParametersException when multiple games/layouts are created, or when no
     *         game elements are specified
     */
    private Game loadGame (String filepath, List<Sprite> nodes, List<Tower> possibleTowers
            ) throws InsufficientParametersException {
        Map<String, Map<String, Map<String, Object>>> myObjects = new HashMap<>();
        for (String partName : PART_NAMES) {
            myObjects.put(partName, new HashMap<>());
        }

        // Get list of parameters maps for all objects
        // in the form of Map<GUID, Map<Parameter Type, Parameter Value>>
        Map<String, Map<String, Object>> allDataObjects = InstanceManager.loadGameData(filepath);

        // Organize parameters maps
        for (Map<String, Object> obj : allDataObjects.values()) {
            String partType = (String) obj.get(PARAMETER_PARTTYPE);
            myObjects.get(partType).put((String) obj.get(PARAMETER_GUID), obj);
        }

        return initializeGame(nodes, myObjects, possibleTowers);
    }

    /**
     * Creates a new Game object given a mapping of all possible objects that can exist in that
     * game. Error checking is done to make sure only one game and layout are used.
     * 
     * @param nodes List<Node> list of javafx nodes that the game can update
     * @param myObjects Map<String, Map<String, Map<String, Object>>> representing mapping of part
     *        name to the specific objects of that type
     * @param possibleTowers
     * @param background
     * @return Game object which has been instantiated with given objects
     * @throws InsufficientParametersException if inputed objects do not fulfill game requirements
     */
    private Game initializeGame (List<Sprite> nodes,
                                 Map<String, Map<String, Map<String, Object>>> myObjects,
                                 List<Tower> possibleTowers) throws InsufficientParametersException {
        // store game parameters
        Game myGame = null;

        // Load game if only one made
        if (myObjects.get("Game").size() != 1) {
            throw new InsufficientParametersException("Zero or multiple game data files created");
        }
        else {
            for (Map<String, Object> map : myObjects.get("Game").values()) {
                myGame = new Game(nodes, map);
            }
        }

        // Load levels if more than 0 made
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

        // Add towers to manager so that upgrade hierarchies can be found
        myTowerManager.add(myObjects.get("Tower"));

        // Tell the player what towers to display
        possibleTowers.addAll(myGame.getAllTowerObjects(myObjects.get("Tower").keySet()));

        return myGame;
    }

    public void startGame (long frameRate) {
        Timeline gameTimeline = new Timeline();
        KeyFrame game = new KeyFrame(Duration.millis(1000 / frameRate), e -> myGame.update());
        gameTimeline.getKeyFrames().add(game);
        gameTimeline.setCycleCount(Animation.INDEFINITE);
        gameTimeline.play();
    }

    /**
     * Called by the player to tell engine about keypresses
     * 
     * @param key KeyEvent object
     */
    public void handleKeyInput (KeyEvent key) {
        // currently not implemented, but would call a method in game
    }

    /**
     * Called by the player to place a game element at some location, such as placing a new tower
     * that the user has bought
     * 
     * @param id GUID of the game element
     * @param sceneX x-coordinate of placement
     * @param sceneY y-coordinate of placement
     */
    public void placeGameElement (String id, double sceneX, double sceneY) {
        // currently only places towers
        myGame.placeTower(id, sceneX, sceneY);
    }
}
