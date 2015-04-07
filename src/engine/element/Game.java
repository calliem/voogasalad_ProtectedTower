package engine.element;

import java.util.ArrayList;
import java.util.List;
import engine.GameElement;
import engine.GameState;
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

    private List<Condition> myConditions;
    private List<Level> myLevels;
    private Layout myLayout;
    private int myActiveLevel;
    public GameState myGameState;

    public Game () {
        myConditions = new ArrayList<Condition>();
        myLevels = new ArrayList<>();
        myLayout = new Layout();
        myActiveLevel = 0;
        myGameState = new GameState();
    }

    public void endGame () {

    }

    @Override
    public void update (int counter) {
        myConditions.forEach(c -> c.act(myGameState));
        myLevels.get(myActiveLevel).update(counter);
        myLayout.update(counter);
    }

    protected void addTower () {

    }

    protected void addEnemy () {

    }
}
