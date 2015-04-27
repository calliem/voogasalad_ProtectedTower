package engine;

import engine.element.sprites.GameElement;


/**
 * This interface includes methods to get a bounding box for collisions
 * 
 * @author Qian Wang
 *
 */
public interface Collidable {

    /**
     * Gets the height of the region where collisions can occur for an object
     * 
     * @return double height
     */
    public double getBoundingHeight ();

    /**
     * Gets the width of the region where collisions can occur for an object
     * 
     * @return double width
     */
    public double getBoundingWidth ();

    /**
     * Gets the x-coordinate of the location for an object
     * 
     * @return double of x-coordinate
     */
    public double getLocationX ();

    /**
     * Gets the y-coordinate of the location for an object
     * 
     * @return double of y-coordinate
     */
    public double getLocationY ();

    /**
     * Provides a default action to occur when a collision occurs with a certain game element
     * 
     * @param element GameElement object which this object collides with
     */
    public abstract void onCollide (GameElement element);
}
