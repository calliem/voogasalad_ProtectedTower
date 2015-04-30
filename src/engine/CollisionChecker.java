package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import engine.element.sprites.GameElement;


/**
 * This class manages finding objects that are colliding or within range of each other. The class
 * uses a Quadtree implemented to find close neighbors and then checks them to see if two objects
 * are within some range. Methods in this class can be called to add members to the Quadtree and
 * then find colliding members.
 * 
 * @author Qian Wang
 * @author Michael Yang
 */
public class CollisionChecker {

    private static final String PARAMETER_RANGE = "Range";
    private static final int INITIAL_QUADTREE_REGIONS = 1;

    /**
     * Quad tree object used for collision
     */
    private Quadtree myQuadTree = null;

    public CollisionChecker () {

    }

    /**
     * Initializes a Quadtree to a certain map with certain bounds
     * 
     * @param bounds Rectangle object representing bounds
     */
    public void initializeQuadtree (Rectangle bounds) {
        myQuadTree = new Quadtree(INITIAL_QUADTREE_REGIONS, bounds);
    }

    /**
     * Clears the quad tree and re-adds all current collidable sprites to the quad tree.
     * 
     * @param sprites List<Sprite> of all sprites on the map
     */
    public void createQuadTree (List<? extends GameElement> sprites) {
        myQuadTree.clear();
        for (GameElement e : sprites) {
            myQuadTree.insert(e);
        }
    }

    /**
     * Checks the collisions for a given sprite against all other sprites on the map and return the
     * other sprites that the specified target collides with.
     * 
     * @param target Sprite object of interest
     * @return Set<GameElement> of other sprites that collide with the target
     */
    public Set<GameElement> findCollisionsFor (GameElement target) {
        Set<GameElement> collisions = new HashSet<>();
        List<GameElement> possibleSprites = getPossibleCollisions(target);
        for (GameElement other : possibleSprites) {
            if (hitBoxCollide(target, other)) {
                collisions.add(other);
            }
        }
        return collisions;
    }

    /**
     * Gives back a list of all possible collisions to a specified sprite. The returned list
     * contains just possible sprites and still needs to be checked that they actually collide.
     * 
     * @param target Sprite to test what objects collide with it
     * @return List<GameElement> of sprites that could collide with the specified target
     */
    private List<GameElement> getPossibleCollisions (GameElement target) {
        List<GameElement> collidable = new ArrayList<>();
        myQuadTree.retrieve(collidable, target);
        return collidable;
    }

    /**
     * Finds the circular bounding range of a sprite in the form of an Circle object.
     * 
     * @param sprite Sprite object
     * @return Circle object representing range of object
     */
    private Circle createRange (GameElement sprite) {
        // TODO add range as an instance variable?
        return new Circle(sprite.getLocationX(), sprite.getLocationY(),
                          (double) sprite.getBoundingHeight());
    }

    /**
     * Finds the rectangular bounding box of a sprite in the form of a Rectangle object.
     * 
     * @param sprite Sprite object
     * @return Rectangle object representing bounds of object
     */
    private Rectangle createHitBox (GameElement sprite) {
        return new Rectangle(sprite.getLocationX(), sprite.getLocationY(),
                             sprite.getBoundingWidth(),
                             sprite.getBoundingHeight());
    }

    /**
     * Finds if two shapes collide using the built in JavaFX intersects() method
     * 
     * @param shape1 first Shape object to check
     * @param shape2 second Shape object to check
     * @return true if the two inputed shapes intersect
     */
    private boolean collides (Shape shape1, Shape shape2) {
        return shape1.getBoundsInParent().intersects(shape2.getBoundsInParent());
    }

    /**
     * Checks if two sprite hitboxes collide
     * 
     * @param sprite1 first Sprite to check
     * @param sprite2 second Sprite to check
     * @return true if the two hitboxes collide
     */

    private boolean hitBoxCollide (GameElement sprite1, GameElement sprite2) {
        return collides(createHitBox(sprite1), createHitBox(sprite2));
    }

    /**
     * Checks if the second sprite is within the range of the first sprite through collisions
     * 
     * @param sprite1 Sprite with range
     * @param sprite2 Sprite to check if in the first sprite's range
     * @return true if the second sprite is in the range of the first one
     */

    private boolean rangeCollide (GameElement sprite1, GameElement sprite2) {
        return collides(createRange(sprite1), createHitBox(sprite2));
    }

    /**
     * Finds all sprites that are within the range of a sprite
     * 
     * @param sprite The sprite that is targeting
     * @return Set<Sprite> that contains all sprites that are within the range of sprite
     */

    public Set<GameElement> findTargetable (GameElement sprite) {
        // TODO Auto-generated method stub
        Set<GameElement> collisions = new HashSet<>();
        List<GameElement> possibleSprites = getPossibleCollisions(sprite);
        for (GameElement other : possibleSprites) {
            if (rangeCollide(sprite, other)) {
                collisions.add(other);
            }
        }
        return collisions;
    }
}
