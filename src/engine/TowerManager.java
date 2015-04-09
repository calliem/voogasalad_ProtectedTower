package engine;

import java.util.HashMap;
import java.util.Map;
import engine.element.sprites.Tower;


/**
 * 
 * This object holds an instance of all the towers available in the game. Towers
 * are stored in trees to track their upgrade paths. A maps pairs each tower
 * name with their respective Tower node for faster lookup.
 * 
 * @author Bojia Chen
 *
 */

public class TowerManager {
    private Map<String, TowerNode> myTowerMap;
    private Map<String, TowerNode> myTreeHeads;

    public TowerManager () {
        myTowerMap = new HashMap<>();
    }

    public void addTower (Tower newTower) {

    }

    public void addTower (Tower attachToTower, Tower newTower) {

    }

}
