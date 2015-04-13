package engine.element.sprites;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import util.reflection.Reflection;
import engine.TowerManager;


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

    public void addManager (TowerManager manager) {
        myTowerManager = manager;
    }

    /**
     * Adds new towers to the list of all possible towers, can be called with a map of tower GUID to
     * the parameters map of that tower, or also with a single GUID and a single parameters map.
     * 
     * @param allSprites Map<String, Map<String, Object>> object
     */
    public void add (Map<String, Map<String, Object>> allSprites) {
        // for (String towerID : allSprites.keySet()) {
        // Map<String, Object> towerProperties = allSprites.get(towerID);
        // this.add(towerID, towerProperties);
        // myTowerManager.addTower(towerID, towerProperties);
        // }
        // TODO refactor into superclass for factories
        allSprites.keySet().forEach(t -> {
            this.add(t, allSprites.get(t));
            myTowerManager.addTower(t, allSprites.get(t));
        });
    }

    /**
     * @see TowerFactory#add(Map)
     * 
     * @param towerID String of the GUID of the tower
     * @param towerProperties the properties Map<String, Object> object of the tower
     */
    public void add (String towerID, Map<String, Object> towerProperties) {
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
