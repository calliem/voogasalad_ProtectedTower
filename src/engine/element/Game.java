package engine.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import util.reflection.Reflection;
import engine.Bank;
import engine.Endable;
import engine.Updateable;
import engine.conditions.Condition;


/**
 * This class holds an instance of an entire tower defense game. It keeps the levels, towers,
 * enemies, and their properties.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Game extends GameElement implements Updateable, Endable {

    private static final String PACKAGE_LOCATION_LEVEL = "engine.element.Level";
    private static final String PARAMETER_HEALTH = "HP";

    /**
     * List of Javafx objects so that new nodes can be added for the player to display
     */
    private List<Node> myNodes;
    private List<Condition> myConditions;
    private List<Level> myLevels;
    private Layout myLayout;
    private int myActiveLevelIndex;
    private Bank myBank;
    private int myPoints;

    public Game (List<Node> nodes) {
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
        myConditions.forEach(c -> c.act((int) super.getParameter(PARAMETER_HEALTH)));
        List<String> enemiesToSpawn = myLevels.get(myActiveLevelIndex).update(counter);
        // TODO update spawn location to correct one
        myLayout.spawnEnemy(enemiesToSpawn, new Point2D(0, 0));
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
            tempLevel.setParameterMap(levels.get(l));
            myLevels.add(tempLevel);
        });
        Collections.sort(myLevels);
    }

    // TODO refactor add methods below
    public void addTowers (Map<String, Map<String, Object>> allObjects) {
        myLayout.initializeTowers(allObjects);
    }

    public void addEnemies (Map<String, Map<String, Object>> allObjects) {
        myLayout.initializeEnemies(allObjects);
    }

    public void addProjectiles (Map<String, Map<String, Object>> allObjects) {
        myLayout.initializeProjectiles(allObjects);
    }

    public void addGridCells (Map<String, Map<String, Object>> allObjects) {
        myLayout.initializeGridCells(allObjects);
    }

    public void addGameMaps (Map<String, Map<String, Object>> allObjects) {
        myLayout.initializeGameMaps(allObjects);
    }

    public void addRounds (Map<String, Map<String, Object>> allObjects) {
        myLayout.initializeRounds(allObjects);
    }

    public void addWaves (Map<String, Map<String, Object>> allObjects) {
        myLayout.initializeWaves(allObjects);
    }

    public void addLayoutParameters (Map<String, Object> parameters) {
        myLayout.setParameterMap(parameters);
    }

    public void placeTower (String id, double sceneX, double sceneY) {
        System.out.println("sup");
        myLayout.placeTower(id, new Point2D(sceneX, sceneY));
    }
}
