package engine;

import java.util.HashSet;
import java.util.Set;
import engine.element.sprites.Tower;


/**
 * This object is the node that is added to the tower trees in TowerManager. It
 * holds the specific tower object itself, the next node, and method for getting
 * new instances of the tower object.
 * 
 * @author Bojia Chen
 *
 */

public class TowerNode {

    private String myName;
    private String myGroup;

    private Set<TowerNode> myNextNodes;
    private Set<TowerNode> myPrevNodes;

    public TowerNode (Tower tower) {
        myName = (String) tower.getParameter("Name");
        myGroup = (String) tower.getParameter("Group");
        myNextNodes = new HashSet<>();
        myPrevNodes = new HashSet<>();
    }

    protected String getName () {
        return myName;
    }

    protected String getGroup () {
        return myGroup;
    }

    protected Tower getTower () {
        // TODO: get new tower from towerFactory
        return null;
    }

    protected boolean addNextNode (TowerNode node) {
        return myNextNodes.add(node);
    }

    protected Set<TowerNode> getNextNode () {

        return myNextNodes;
    }

    protected boolean removeNextNode (TowerNode node) {
        return myNextNodes.remove(node);
    }

    protected boolean addPrevNode (TowerNode node) {
        return myPrevNodes.add(node);
    }

    protected Set<TowerNode> getPrevNode () {
        return myPrevNodes;
    }

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
        return (myName + myGroup).hashCode();
    }
}
