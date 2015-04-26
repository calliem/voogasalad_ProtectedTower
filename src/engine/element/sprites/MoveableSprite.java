package engine.element.sprites;

import annotations.parameter;
import javafx.geometry.Point2D;


/**
 * This class represents game elements that move around the game space. Required abstract methods
 * include those to control movement. A variable representing velocity can be used to move.
 * 
 * @author Qian Wang
 *
 */
public abstract class MoveableSprite extends Sprite {

    /**
     * Holds the current heading of the sprite
     */
    @parameter(playerDisplay = false)
    private Point2D myHeading;

    // private double myRange;

    // Setters and getters

    /**
     * Sets the heading of the sprite to a normalized value
     * 
     * @param heading Point2D object representing x and y components of heading
     */
    protected void setHeading (Point2D velocity) {
        myHeading = velocity.normalize();
    }

    /**
     * Gets the heading of the sprite, returns a normalized value
     * 
     * @return normalized Point2D representing the heading of the object
     */
    protected Point2D getHeading () {
        return myHeading;
    }

    /**
     * Sets the velocity of the sprite to a normalized value
     * 
     * @param x double of x component of velocity
     * @param y double of y component of velocity
     */
    // protected void setVelocity (double x, double y) {
    // myVelocity = new Point2D(x, y).normalize();
    // }

    /**
     * Sets the range of the sprite movement. This value is used to test is the sprite has traveled
     * past its rated range, in which case it can stop moving.
     * 
     * @param range double value of the range the sprite should move before stopping
     */
    // protected void setRange (double range) {
    // myRange = range;
    // }

    // Abstract methods

    /**
     * This method is called when this object targets another object to perform an action, such as
     * applying a status effect, or damaging the the object.
     * 
     * @param sprite Sprite object that is targeted
     */
    public abstract void target (Sprite sprite);

    /**
     * This method is called by the game loop to update the view of the MovableSprite object. This
     * method should be used to define how this object moves and changes coordinates on the screen.
     */
    public abstract void move ();

}
