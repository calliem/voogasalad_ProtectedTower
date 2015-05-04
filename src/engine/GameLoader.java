// This entire file is part of my masterpiece.
// Qian Wang

package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.engine.PropertiesFileLoader;
import authoringEnvironment.GameCreator;
import authoringEnvironment.InstanceManager;
import engine.element.Game;
import engine.element.sprites.Sprite;
import engine.element.sprites.Tower;


/**
 * This class will load a game given the file location, as well as set up the observed lists in the
 * player that are passed in.
 * 
 * @author Qian Wang
 *
 */
public class GameLoader {

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
    public static Game loadGame (String filepath, List<Sprite> nodes, List<Tower> possibleTowers
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
    private static Game initializeGame (List<Sprite> nodes,
                                        Map<String, Map<String, Map<String, Object>>> myObjects,
                                        List<Tower> possibleTowers)
                                                                   throws InsufficientParametersException {
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

        // Tell the player what towers to display
        possibleTowers.addAll(myGame.getAllTowerObjects(myObjects.get("Tower").keySet()));

        return myGame;
    }
}
