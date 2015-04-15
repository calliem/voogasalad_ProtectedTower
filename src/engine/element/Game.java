package engine.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import util.reflection.Reflection;
import engine.Bank;
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
public class Game extends GameElement implements Updateable {

    private static final String PACKAGE_LOCATION_LEVEL = "engine.element.Level";
    private static final String PARAMETER_HEALTH = "HP";

    private List<Condition> myConditions;
    private List<Level> myLevels;
    private Layout myLayout;
    private int myActiveLevel;
    private Bank myBank;
    private int myPoints;
    /**
     * List of Javafx objects so that new nodes can be added for the player to
     * display
     */
    private List<Node> myNodes;

    public Game (List<Node> nodes) {
        myConditions = new ArrayList<Condition>();
        myLevels = new ArrayList<>();
        myNodes = nodes;
        myLayout = new Layout(myNodes);
        myActiveLevel = 0;
        myBank = new Bank();
        myPoints = 0;
    }

    public void endGame () {

    }

    @Override
    public void update (int counter) {
        myConditions.forEach(c -> c.act((int) super.getParameter(PARAMETER_HEALTH)));
        myLevels.get(myActiveLevel).update(counter);
        myLayout.update(counter);
    }

    /**
     * Adds levels to the list of all levels, fills their parameter maps, and sorts levels by number
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

    public void addLayoutParameters (Map<String, Object> parameters) {
        myLayout.setParameterMap(parameters);
    }
}
