package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import engine.conditions.Condition;


/**
 * This class holds an instance of an entire tower defense game. It keeps the levels, towers,
 * enemies, and their properties.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Game implements Updateable {

    /**
     * Holds the parameters of the game, like lives remaining
     */
    private Map<String, Object> myParameters;
    private Bank myBank;
    private List<Condition> myConditions;
    private int myPoints;
    private List<Level> myLevels;
    private Layout myLayout;
    private int myActiveLevel;

    public Game () {
        myBank = new Bank();
        myConditions = new ArrayList<Condition>();
        myPoints = 0;
        myLevels = new ArrayList<>();
        myLayout = new Layout();
        myActiveLevel = 0;
    }

    public void endGame () {

    }

    @Override
    public void update (int counter) {
        // TODO Auto-generated method stub
        myLevels.get(myActiveLevel).update(counter);
        myLayout.update(counter);
    }

}
