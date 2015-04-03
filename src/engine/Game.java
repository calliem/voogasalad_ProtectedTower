package engine;

import java.util.ArrayList;
import engine.conditions.Condition;


/**
 * This class holds an instance of an entire tower defense game. It keeps the levels, towers,
 * enemies, and their properties.
 * 
 * @author Qian Wang
 *
 */
public class Game {

    private Bank myBank;
    ArrayList<Condition> myConditions;

    public Game () {
        myBank = new Bank();
        myConditions = new ArrayList<Condition>();
    }

    public void endGame () {
    }

}
