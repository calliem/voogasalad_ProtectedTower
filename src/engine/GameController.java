package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import authoringEnvironment.GameCreator;
import authoringEnvironment.InstanceManager;
import engine.element.Game;
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
     * Holds an instance of an entire game
     */
    Game myGame;
    /**
     * Holds a map of a part name to the package to use to reflect
     */
    Map<String, String> myPartTypeToPackage = new HashMap<>();
    /**
     * List of Javafx objects so that new nodes can be added for the player to
     * display
     */
    private List<Node> myNodes;
    public GameController (String filepath, List<Node> nodes, List<Tower> possibleTowers)
        throws InsufficientParametersException {
       // fillPackageMap();
        myNodes = nodes;
        //myGame = this.loadGame(filepath);
        myGame = new Game(myNodes);
    }
    public void startGame(long frameRate){
        Timeline gameTimeline = new Timeline();
        KeyFrame game = new KeyFrame(Duration.millis(1/frameRate),
                        e -> myGame.update((int)(Integer.parseInt(gameTimeline.currentTimeProperty().toString())/(1/frameRate))));
        gameTimeline.getKeyFrames().add(game);
        gameTimeline.play();
    }
    // TODO replace this with loading from data file
    private void fillPackageMap () {
        myPartTypeToPackage.put("Tower", "engine.element.sprites.Tower");
        myPartTypeToPackage.put("Enemy", "engine.element.sprites.Enemy");
        myPartTypeToPackage.put("Projectile",
                                "engine.element.sprites.Projectile");
        myPartTypeToPackage.put("GridCell", "engine.element.sprites.GridCell");
        myPartTypeToPackage.put("Game", "engine.element.Game");
        myPartTypeToPackage.put("Level", "engine.element.Level");
        myPartTypeToPackage.put("Round", "engine.element.Round");
        myPartTypeToPackage.put("Wave", "engine.element.Wave");
        myPartTypeToPackage.put("Layout", "engine.element.Layout");
    }

    /**
     * Given a location of a game file, the {@link GameCreator#loadGame(String)} method if called,
     * which generates a map of objects names to the
     * parameters which those objects should contain. Those objects are then
     * instantiated and their parameter lists are set.
     * 
     * @param filepath
     *        String of location of the game file
     * @throws InsufficientParametersException
     *         when multiple games/layouts are created, or when no game
     *         elements are specified
     */
    private Game loadGame (String filepath)
                                           throws InsufficientParametersException {
        Map<String, Map<String, Map<String, Object>>> myObjects = new HashMap<>();
        for (String s : myPartTypeToPackage.keySet()) {
            myObjects.put(s, new HashMap<>());
        }

        // Get list of parameters maps for all objects
        // TODO change to collection or set
        Map<String, Map<String, Object>> allDataObjects = InstanceManager
                .loadGameData(filepath);

        // Organize parameters maps
        for (String key : allDataObjects.keySet()) {
            Map<String, Object> obj = allDataObjects.get(key);
            String partType = (String) obj.get(PARAMETER_PARTTYPE);

            // System.out.println(obj);
            myObjects.get(partType).put((String) obj.get(PARAMETER_GUID), obj);
            System.out.println((String) obj.get(PARAMETER_GUID));
        }

        // store game parameters
        Game myGame = new Game(myNodes);

        // TODO test for errors for 0 data files, or too many
        if (myObjects.get("Game").size() > 1) {
            throw new InsufficientParametersException(
                                                      "Multiple game data files created");
        }
        else {
            for (Map<String, Object> map : myObjects.get("Game").values()) {
                myGame.setParameterMap(map);
            }
        }
        if (myObjects.get("Layout").size() > 1) {
            throw new InsufficientParametersException(
                                                      "Multiple game layouts created");
        }
        else {
            for (Map<String, Object> map : myObjects.get("Layout").values()) {
                myGame.addLayoutParameters(map);
            }
        }

        // Send right sets of objects to the right objects
        myGame.addTowers(myObjects.get("Tower"));
        myGame.addEnemies(myObjects.get("Enemy"));
        myGame.addProjectiles(myObjects.get("Projectile"));
        myGame.addGridCells(myObjects.get("GridCell"));
        myGame.addLevels(myObjects.get("Level"));
        // TODO add rounds to levels
        // TODO add rounds and waves to factory

        return myGame;
    }

    /**
     * Called by the player to tell engine about keypressed
     * 
     * @param key
     *        KeyEvent object
     */
    public void handleKeyInput (KeyEvent key) {

    }

    public static void main (String[] args)
                                           throws InsufficientParametersException {
        // GameController test =
        // new GameController(
        // "src\\exampleUserData\\TestingManagerGame\\TestingManagerGame.gamefile");
    }
    public void addPlaceable (String id, double sceneX, double sceneY) {
        // TODO Auto-generated method stub
        myGame.placeTower(id, sceneX, sceneY);
    }
}
