package engine.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import engine.GameState;
import engine.TowerManager;
import engine.Updateable;
import engine.conditions.Condition;
import engine.element.sprites.Tower;
import engine.element.sprites.TowerFactory;


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
    private TowerFactory myTowerFactory;

    public Game () {
        myConditions = new ArrayList<Condition>();
        myLevels = new ArrayList<>();
        
        myTowerFactory = new TowerFactory();
        TowerManager manager = new TowerManager(myTowerFactory);
        myTowerFactory.addManager(manager);
        
        myLayout = new Layout(manager);
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

    protected void addTowers(Map<String,Map<String,Object>> allTowers) {
        myTowerFactory.addTower(allTowers);
    }

    protected void addEnemy () {

    }
}
