package engine;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This object is the node that is added to the tower trees in TowerManager. It
 * holds the specific tower object itself, the next node, and method for getting
 * new instances of the tower object.
 * 
 * @author Bojia Chen
 *
 */

public class TowerNode {

    private final static String PARAM_NAME = "Name";
    private final static String PARAM_GROUP = "Group";
    private final static String PARAM_NEXT_TOWER = "NextTower";

    private String myName;
    private String myGroup;

    /**
     * Set of nodes immediately after the current node
     */
    private Set<TowerNode> myNextNodes;

    /**
     * Set of nodes immediately before the current node
     */
    private Set<TowerNode> myPrevNodes;

    private Map<String, Object> myParameters;

    public TowerNode (Map<String, Object> parameters) {
        myName = (String) parameters.get(PARAM_NAME);
        myGroup = (String) parameters.get(PARAM_GROUP);
        myNextNodes = new HashSet<>();
        myPrevNodes = new HashSet<>();
        myParameters = parameters;
    }

    protected String getName () {
        return myName;
    }

    protected String getGroup () {
        return myGroup;
    }

    protected Map<String, Object> getTowerParameters () {
        return myParameters;
    }

    protected boolean addNextNode (TowerNode node) {
        return myNextNodes.add(node);
    }

    /*
     * protected Set<TowerNode> getNextNodes () {
     * return myNextNodes;
     * }
     */

    protected List<String> getNextNodes () {
        return (List<String>) myParameters.get(PARAM_NEXT_TOWER);
    }

    protected boolean removeNextNode (TowerNode node) {
        return myNextNodes.remove(node);
    }

    protected boolean addPrevNode (TowerNode node) {
        return myPrevNodes.add(node);
    }

    /*
     * protected Set<TowerNode> getPrevNodes () {
     * return myPrevNodes;
     * }
     */

    protected boolean removePrevNode (TowerNode node) {
        return myPrevNodes.remove(node);
    }

    /**
     * Overriding hashcode and equals
     */

    @Override
    public boolean equals (Object o) {
        if (!(o instanceof TowerNode))
            return false;
        if (o == this)
            return true;
        TowerNode node = (TowerNode) o;
        return myName.equals(node.getName()) && myGroup.equals(node.getGroup());
    }

    @Override
    public int hashCode () {
        return (myGroup + "_" + myName).hashCode();
    }
}
