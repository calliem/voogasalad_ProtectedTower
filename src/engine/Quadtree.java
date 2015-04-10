package engine;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;
import engine.element.sprites.Tower;


/**
 * Implements a quadtree specifically for use in sprite collision detection.
 * 
 * @author Greg McKeon, Stephen Lambert
 *         (http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-
 *         quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374)
 * 
 *
 */
public class Quadtree {
    private int MAX_OBJECTS = 10;
    private int MAX_LEVELS = 5;
    private int level;
    private List<Collidable> objects;
    private Rectangle bounds;
    private Quadtree[] nodes;

    /*
     * Constructor
     */
    public Quadtree (int pLevel, Rectangle pBounds) {
        level = pLevel;
        objects = new ArrayList<>();
        bounds = pBounds;
        nodes = new Quadtree[4];
    }

    /**
     * Clears the quadtree
     */
    public void clear () {
        objects.clear();
        for (Quadtree node : nodes) {
            if (node != null) {
                node.clear();
                node = null;
            }
        }
    }

    /**
     * Splits the node into 4 subnodes
     */
    private void split () {
        int subWidth = (int) (bounds.getWidth() / 2);
        int subHeight = (int) (bounds.getHeight() / 2);
        int x = (int) bounds.getX();
        int y = (int) bounds.getY();

        nodes[0] = new Quadtree(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new Quadtree(level + 1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new Quadtree(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] =
                new Quadtree(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth,
                                                      subHeight));
    }

    /**
     * Determine which node the object belongs to. -1 means object cannot completely fit within a
     * child node and is part of the parent node
     */
    private int getIndex (Collidable spriteToCheck) {
        int index = -1;
        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

        // Object can completely fit within the top quadrants
        boolean topQuadrant =
                (spriteToCheck.getLocationY() < horizontalMidpoint && spriteToCheck.getLocationY() +
                                                                      spriteToCheck
                                                                              .getBoundingHeight() < horizontalMidpoint);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (spriteToCheck.getLocationY() > horizontalMidpoint);

        // Object can completely fit within the left quadrants
        if (spriteToCheck.getLocationX() < verticalMidpoint &&
            spriteToCheck.getLocationX() + spriteToCheck.getBoundingWidth() < verticalMidpoint) {
            if (topQuadrant) {
                index = 1;
            }
            else if (bottomQuadrant) {
                index = 2;
            }
        }
        // Object can completely fit within the right quadrants
        else if (spriteToCheck.getLocationX() > verticalMidpoint) {
            if (topQuadrant) {
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }

        return index;
    }

    /**
     * Insert the object into the quadtree. If the node exceeds the capacity, it will split and add
     * all objects to their corresponding nodes.
     */
    public void insert (Collidable pRect) {
        if (nodes[0] != null) {
            int index = getIndex(pRect);
            if (index != -1) {
                nodes[index].insert(pRect);
                return;
            }
        }

        objects.add(pRect);

        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            while (i < objects.size()) {
                int index = getIndex(objects.get(i));
                if (index != -1) {
                    nodes[index].insert(objects.remove(i));
                }
                else {
                    i++;
                }
            }
        }
    }

    /*
     * Return all objects that could collide with the given object
     */
    public List<Collidable> retrieve (List<Collidable> returnObjects, Collidable spriteToCheck) {
        int index = getIndex(spriteToCheck);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, spriteToCheck);
        }
        returnObjects.addAll(objects);
        return returnObjects;
    }

    /*public static void main (String[] args) throws InsufficientParametersException {
        Quadtree q = new Quadtree(3, new Rectangle(3, 4));
        Tower e = new Tower();
        q.insert(e);
    }*/
}
