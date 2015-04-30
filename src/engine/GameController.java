package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import testing.ExampleGame;
import testing.ExampleLevel;
import testing.ExampleRound;
import testing.ExampleWave;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
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
    private static final String[] PART_NAMES = new String[] { "Tower", "Enemy", "Projectile",
                                                             "GridCell", "GameMap", "Round",
                                                             "Wave", "Game", "Level", "MapPath" };
    // TODO do we pull in a Layout object map?
    /**
     * Holds a subset of part names to give to the game element factory
     */
    private static final String[] FACTORY_PART_NAMES = new String[] { "Tower", "Enemy",
                                                                     "Projectile", "GridCell",
                                                                     "GameMap", "Round", "Wave",
                                                                     "MapPath" };

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
                           List<Tower> possibleTowers,
                           Group background)
        throws InsufficientParametersException {
        myTowerManager = new TowerManager();
        myGame = this.loadGame(filepath, nodes, possibleTowers, background);
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
    private Game loadGame (String filepath, List<Sprite> nodes, List<Tower> possibleTowers,
                           Group background) throws InsufficientParametersException {
        Map<String, Map<String, Map<String, Object>>> myObjects = new HashMap<>();
        for (String partName : PART_NAMES) {
            myObjects.put(partName, new HashMap<>());
        }

        // Get list of parameters maps for all objects
        // Map<GUID, Map<ParamType, ParamValue>>
        // TODO change to collection or set
        Map<String, Map<String, Object>> allDataObjects = InstanceManager.loadGameData(filepath);

        // Organize parameters maps
        for (Map<String, Object> obj : allDataObjects.values()) {
            String partType = (String) obj.get(PARAMETER_PARTTYPE);
            myObjects.get(partType).put((String) obj.get(PARAMETER_GUID), obj);
            System.out.println(obj.get("Image"));
        }
        System.out.println("===================================================");

        return initializeGame(nodes, myObjects, possibleTowers, background);
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
                                 List<Tower> possibleTowers,
                                 Group background) throws InsufficientParametersException {
        // store game parameters
        Game myGame = new Game(nodes, background, ExampleGame.generateExampleGame());

        // TODO test for errors for 0 data files, or too many
        // if (myObjects.get("Game").size() != 1) {
        // throw new InsufficientParametersException("Zero or multiple game data files created");
        // }
        // else {
        // for (Map<String, Object> map : myObjects.get("Game").values()) {
        // // TODO need game factory or something to initialize it
        // }
        // }

        // if (myObjects.get("Level").size() < 1) {
        // throw new InsufficientParametersException("No game levels created");
        // }
        // else {
        // myGame.addLevels(myObjects.get("Level"));
        // }

        // TODO: ADDING LEVELS
        myGame.addLevels(ExampleLevel.generateExampleLevel());

        // Send right sets of objects to the right objects
        for (String partName : FACTORY_PART_NAMES) {
            myGame.addGameElement(partName, myObjects.get(partName));
        }

        myGame.addGameElement("Round", ExampleRound.generateExampleRound());
        myGame.addGameElement("Wave", ExampleWave.generateExampleWave());

        System.out.println("===================================================");
        // TODO: POPULATING TOWER MANAGER
        myTowerManager.add(myObjects.get("Tower"));

        possibleTowers.addAll(myGame.getAllTowerObjects(myObjects.get("Tower").keySet()));

        System.out.println("===================================================");
        return myGame;
    }

    public void startGame (long frameRate) {
        Timeline gameTimeline = new Timeline();
        KeyFrame game =
                new KeyFrame(Duration.millis(1 / frameRate),
                             e -> myGame.update((int) (Integer.parseInt(gameTimeline
                                     .currentTimeProperty().toString()) / (1 / frameRate))));
        gameTimeline.getKeyFrames().add(game);
        gameTimeline.play();
    }

    /**
     * Called by the player to tell engine about keypressed
     * 
     * @param key KeyEvent object
     */
    public void handleKeyInput (KeyEvent key) {

    }

    public void addPlaceable (String id, double sceneX, double sceneY) {
        // TODO Auto-generated method stub
        myGame.placeTower(id, sceneX, sceneY);
    }

    public static void main (String[] args) throws InsufficientParametersException {
        System.out.println(new ArrayList<Sprite>());
        GameController test =
                new GameController(
                                   "data//DesktopTDTest//DesktopTD//DesktopTD.gamefile",
                                   new ArrayList<Sprite>(), new ArrayList<Tower>(), new Group());
    }
}
