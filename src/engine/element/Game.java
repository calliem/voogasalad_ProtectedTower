package engine.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import util.reflection.Reflection;
import annotations.parameter;
import engine.Bank;
import engine.Endable;
import engine.Updateable;
import engine.conditions.Condition;
import engine.element.sprites.Sprite;


/**
 * This class holds an instance of an entire tower defense game. It keeps the levels, towers,
 * enemies, and their properties.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Game implements Updateable, Endable {

    private static final String PACKAGE_LOCATION_LEVEL = "engine.element.Level";

    @parameter(settable = true, playerDisplay = true, defaultValue = "20")
    private Integer lives;

    /**
     * List of Javafx objects so that new nodes can be added for the player to display
     */
    private List<Sprite> myNodes;
    private List<Condition> myConditions;
    private List<Level> myLevels;
    private Layout myLayout;
    private int myActiveLevelIndex;
    private Bank myBank;
    private int myPoints;

    public Game (List<Sprite> nodes) {
        myConditions = new ArrayList<Condition>();
        myLevels = new ArrayList<>();
        myNodes = nodes;
        myLayout = new Layout(myNodes);
        myActiveLevelIndex = 0;
        myBank = new Bank();
        myPoints = 0;
    }

    /**
     * Checks if the user has played through all levels.
     * 
     * @return true if all levels have ended
     */
    public boolean hasEnded () {
        return myActiveLevelIndex >= myLevels.size();
    }

    /**
     * Calls update methods on the current Level object and then the Layout object. Checks game
     * conditions for win/lose.
     * 
     * @see Updateable#update(int)
     */
    @Override
    public void update (int counter) {
        System.out.println("Beginning cycle " + counter);
        myConditions.forEach(c -> c.check());
        Map<Object, List<String>> enemiesToSpawn =
                myLevels.get(myActiveLevelIndex).update(counter);
        for (Object loc : enemiesToSpawn.keySet()) {
            myLayout.spawnEnemy(enemiesToSpawn.get(loc), (String) loc);
        }
        myLayout.update(counter);
    }

    /**
     * Adds levels to the list of all levels, fills their parameter maps, and sorts levels by
     * number. This method is called by the GameController to add levels when loading the game.
     * 
     * @param levels Map<String, Map<String, Object>> mapping GUID to parameters map for each level
     */
    public void addLevels (Map<String, Map<String, Object>> levels) {
        levels.keySet().forEach(l -> {
            Level tempLevel = (Level) Reflection.createInstance(PACKAGE_LOCATION_LEVEL);
            // TODO maybe need to add levels factory
            myLevels.add(tempLevel);
        });
        Collections.sort(myLevels);
    }

    /**
     * Used to tell the Layout what possible objects there are to instantiate
     * 
     * @param className String of the class of the object, such as "Tower" or "Enemy"
     * @param allObjects Map<String, Map<String, Object>> object representing mapping of GUID to
     *        parameter map
     */
    public void addGameElement (String className, Map<String, Map<String, Object>> allObjects) {
        myLayout.initializeGameElement(className, allObjects);
    }

    public void placeTower (String id, double sceneX, double sceneY) {
        myLayout.placeTower(id, new Point2D(sceneX, sceneY));
    }
}
