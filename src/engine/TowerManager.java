package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import engine.element.sprites.Tower;
import engine.sprites.TowerFactory;


/**
 * 
 * This object holds an instance of all the towers available in the game. Towers
 * are stored in trees to track their upgrade paths. A maps pairs each tower
 * name with their respective Tower node for faster lookup.
 * 
 * In this implementation, TowerFactory adds towers into the map using addTower
 * 
 * @author Bojia Chen
 *
 */

public class TowerManager {
    private static final String NEXT_TOWER = "NextTower";
    private Map<String, TowerNode> myTowerMap;
    // private Map<String, TowerNode> myTreeHeads;
    private TowerFactory myTowerFactory;

    public TowerManager (TowerFactory factory) {
        myTowerMap = new HashMap<>();
        myTowerFactory = factory;
    }

    @SuppressWarnings("unchecked")
    public void addAllTowers (Map<String, Map<String, Object>> towers) {
        for (String s : towers.keySet()) {
            myTowerMap.put(s, new TowerNode(towers.get(s)));
        }
        for (String s : myTowerMap.keySet()) {
            // TODO find way to do this without casting
            for (String n : (List<String>) towers.get(s).get(NEXT_TOWER)) {
                myTowerMap.get(s).addNextNode(myTowerMap.get(n));
            }
        }
    }
    
    //TODO: Update tower health on upgrade/downgrade. Downgrade health is MIN(Current_health, Max_Health)

    public void addTower (String towerID, Map<String, Object> towerProperties) {
        TowerNode node = new TowerNode(towerProperties);
        myTowerMap.put(towerID, node);
    }

    public List<String> getNextTowers (String towerID) {
        TowerNode node = myTowerMap.get(towerID);
        return node.getNextNodes();
    }

    public Tower getTower (String towerID) {
        return myTowerFactory.getTower(towerID);
    }

}
