package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

    private final static String PARAMETER_NEXT_TOWER = "NextSprite";
    private final static String PARAMETER_TOWER_GROUP = "Group";
    private Map<String, TowerNode> myTowerMap;
    private Map<String, TowerNode> myTreeRoots = new HashMap<String, TowerNode>();

    // private Map<String, TowerNode> myTreeHeads;

    public TowerManager () {
        myTowerMap = new HashMap<>();
    }

    /**
     * Adds new towers to the list of all possible towers. This method can be called
     * with a map of GUID to parameter map, or with a single GUID and single parameter map.
     * 
     * @param allTowers Map<String, Map<String, Object>> object representing mapping of GUID to
     *        parameter map
     */
    public void add (Map<String, Map<String, Object>> allTowers) {
        allTowers.keySet().forEach(t -> this.add(t, allTowers.get(t)));
    }

    /**
     * @see TowerManager#add(Map)
     * 
     * @param guid GUID of the tower being added
     * @param towerProperties Map of tower parameters and their values
     */
    public void add (String guid, Map<String, Object> towerProperties) {
        TowerNode newNode = new TowerNode(towerProperties);
        String towerGroup = (String) towerProperties.get(PARAMETER_TOWER_GROUP);
        myTowerMap.put(guid, newNode);
        if (!myTreeRoots.containsKey(towerGroup)) {
            myTreeRoots.put(towerGroup, newNode);
        }
        myTowerMap.get(guid).addNextNode(myTowerMap.get(towerProperties.get(PARAMETER_NEXT_TOWER)));
    }

    /**
     * Looks up root TowerNode given the GUID of any tower in the tree for that group tree
     * 
     * @param guid GUID of a tower in the group from which to grab the root TowerNode
     * @return root TowerNode for specified group
     */
    public TowerNode getTreeByGUID (String guid) {
        return getTreeByGroup(getTowerGroup(guid));
    }

    /**
     * Looks up root TowerNode of the specified tower group
     * 
     * @param group Name of tower group from which to grab the root TowerNode
     * @return root TowerNode for specified group
     */
    public TowerNode getTreeByGroup (String group) {
        return myTreeRoots.get(group);
    }

    /**
     * Looks up set of root TreeNodes for all the trees in TowerManager
     * 
     * @return Set of all root TreeNodes
     */
    public Set<TowerNode> getAllTreeRoots () {
        return (Set<TowerNode>) myTreeRoots.values();
    }

    /*
     * Full health is included in the cost of a tower upgrade. When downgrading, age and remaining
     * health determine how much money the player gets back.
     */

    /**
     * 
     * @param towerID GUID of the tower object from which to request nextTower information
     * @return List of GUIDs of towers immediately following the specified one
     */
    public Set<TowerNode> getNextTowers (String towerID) {
        TowerNode node = myTowerMap.get(towerID);
        return node.getNextNodes();
    }

    /**
     * Looks up the group name of a tower given its GUID
     * 
     * @param guid GUID of tower
     * @return group name
     */
    private String getTowerGroup (String guid) {
        return myTowerMap.get(guid).getGroup();
    }

}
