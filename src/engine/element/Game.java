package engine.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    private static final String PARAMETER_HEALTH = "HP";

    private List<Condition> myConditions;
    private List<Level> myLevels;
    private Layout myLayout;
    private int myActiveLevel;
    // public GameState myGameState;
    private Bank myBank;
    private int myPoints;

    public Game () {
        myConditions = new ArrayList<Condition>();
        myLevels = new ArrayList<>();

        myLayout = new Layout();
        myActiveLevel = 0;
        // myGameState = new GameState();
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
}
