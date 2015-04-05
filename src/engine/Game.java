package engine;

import java.util.ArrayList;
import java.util.List;
import engine.conditions.Condition;


/**
 * This class holds an instance of an entire tower defense game. It keeps the levels, towers,
 * enemies, and their properties.
 * 
 * @author Qian Wang
 * @author Bojia Chen
 *
 */
public class Game {

    private Bank myBank;
    private List<Condition> myConditions;
    private int myExperiencePoints;
    private List<Level> myLevels;

    public Game () {
        myBank = new Bank();
        myConditions = new ArrayList<Condition>();
        myExperiencePoints = 0;
        myLevels = new ArrayList<>();
    }

    public void endGame () {
    }

}
