package engine;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.reflection.Reflection;
import engine.element.sprites.Tower;


/**
 * 
 * This object holds an instance of all the towers available in the game. Towers
 * are stored in trees to track their upgrade paths. A maps pairs each tower
 * name with their respective TowerNode for faster lookup.
 * 
 * Previous TowerFactory code has been merged in with this class to allow tower lookup and
 * generation within the same class
 * 
 * @author Bojia Chen
 *
 */

public class TowerManager {

    private final static String MY_CLASS_NAME = "engine.sprites.Tower";
    private static final String NEXT_TOWER = "NextTower";
    private Map<String, TowerNode> myTowerMap;

    // private Map<String, TowerNode> myTreeHeads;

    public TowerManager () {
        myTowerMap = new HashMap<>();
    }

    /*
     * TODO: Update tower health on upgrade/downgrade. Downgrade health is
     * MIN(Current_health,Max_Health)
     */

    /**
     * Method for adding in individual towers to the local TowerMap
     * 
     * @param towerID GUID of the tower being added
     * @param towerProperties Map of tower parameters and their values
     */
    public void addTower (String towerID, Map<String, Object> towerProperties) {
        myTowerMap.put(towerID, new TowerNode(towerProperties));

        // TODO find way to do this without casting
        for (String n : (List<String>) towerProperties.get(NEXT_TOWER)) {
            myTowerMap.get(towerID).addNextNode(myTowerMap.get(n));
        }
    }

    /**
     * Method for adding in a group of towers to the local TowerMap
     * 
     * @param allTowers Map of all towers to be added where the first string is the GUID and the
     *        internal map is the parameter map
     */
    public void addTower (Map<String, Map<String, Object>> allTowers) {
        for (String towerID : allTowers.keySet()) {
            Map<String, Object> towerProperties = allTowers.get(towerID);
            addTower(towerID, towerProperties);
        }
    }

    /**
     * Method for getting a new instance of a specific tower
     * 
     * @param towerID GUID of the desired tower
     * @return New tower object with the parameters of the tower corresponding with the provided
     *         GUID
     */

    public Tower getTower (String towerID) {
        if (!myTowerMap.containsKey(towerID)) {
            throw new InvalidParameterException(towerID
                                                + " is an undefined tower");
        }

        Tower tower = (Tower) Reflection.createInstance(MY_CLASS_NAME);
        tower.setParameterMap(myTowerMap.get(towerID).getTowerParameters());

        return tower;
    }

    /**
     * 
     * @param towerID GUID of the tower object from which to request nextTower information
     * @return List of GUIDs of towers immediately following the specified one
     */

    public List<String> getNextTowers (String towerID) {
        TowerNode node = myTowerMap.get(towerID);
        return node.getNextNodes();
    }

}
