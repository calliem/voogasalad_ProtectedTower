package engine.element.sprites;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.reflection.Reflection;


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

public class TowerManager extends SpriteFactory {

    private final static String PARAMETER_MY_CLASS_NAME = "engine.element.sprites.Tower";
    private final static String PARAMETER_NEXT_TOWER = "NextTower";
    private Map<String, TowerNode> myTowerMap;

    // private Map<String, TowerNode> myTreeHeads;

    public TowerManager () {
        super(PARAMETER_MY_CLASS_NAME);
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
    public void add (String towerID, Map<String, Object> towerProperties) {
        super.add(towerID, towerProperties);
        myTowerMap.put(towerID, new TowerNode(towerProperties));

        // TODO find way to do this without casting
        for (String n : (List<String>) towerProperties.get(PARAMETER_NEXT_TOWER)) {
            myTowerMap.get(towerID).addNextNode(myTowerMap.get(n));
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
        super.checkID(towerID);

        Tower tower = (Tower) Reflection.createInstance(PARAMETER_MY_CLASS_NAME);
        tower.setParameterMap(super.getParameters(towerID));

        return tower;
    }

    /**
     * 
     * @param towerID GUID of the tower object from which to request nextTower information
     * @return List of GUIDs of towers immediately following the specified one
     */

    public Set<TowerNode> getNextTowers (String towerID) {
        TowerNode node = myTowerMap.get(towerID);
        return node.getNextNodes();
    }

}
