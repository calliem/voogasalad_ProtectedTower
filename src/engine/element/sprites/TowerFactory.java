package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;
<<<<<<< HEAD:src/engine/sprites/TowerFactory.java
import engine.TowerManager;
import engine.element.sprites.Tower;
=======
>>>>>>> master:src/engine/element/sprites/TowerFactory.java


/**
 * Factory for producing towers
 * 
 * @author Bojia Chen
 *
 */

public class TowerFactory {

    private Map<String, Map<String, Object>> myTowers;
    private final static String MY_CLASS_NAME = "engine.sprites.Tower";
    private TowerManager myTowerManager;

    public TowerFactory () {
        myTowers = new HashMap<>();
    }
    
    public void addManager(TowerManager manager) {
        myTowerManager = manager;
    }
    
    public void addTower (Map<String, Map<String, Object>> allTowers) {
        for (String towerID : allTowers.keySet()) {
            Map<String, Object> towerProperties = allTowers.get(towerID);
            addTower(towerID, towerProperties);
            myTowerManager.addTower(towerID, towerProperties);
        }
    }

    public void addTower (String towerID, Map<String, Object> towerProperties) {
        myTowers.put(towerID, towerProperties);
    }

    public Tower getTower (String userInput) {
        if (!myTowers.containsKey(userInput)) { throw new InvalidParameterException(userInput +
                                                                                    " is an undefined tower"); }

        Tower tower = (Tower) Reflection.createInstance(MY_CLASS_NAME);
        tower.setParameterMap(myTowers.get(userInput));

        return tower;
    }
}
