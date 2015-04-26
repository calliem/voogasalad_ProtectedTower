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

    @parameter(settable = true, playerDisplay = true, defaultValue = "1.0")
    private Double speed;
    @parameter(settable = true, playerDisplay = true)
    private String group = null;
    /**
     * Holds the current heading of the sprite
     */
    @parameter(playerDisplay = true)
    private Point2D heading;

    // private double myRange;

    // Setters and getters

    /**
     * Sets the heading of the sprite to a normalized value
     * 
     * @param heading Point2D object representing x and y components of heading
     */
    protected void setHeading (Point2D velocity) {
        heading = velocity.normalize();
    }

    /**
     * Gets the heading of the sprite, returns a normalized value
     * 
     * @return normalized Point2D representing the heading of the object
     */
    protected Point2D getHeading () {
        return heading;
    }

    public Double getSpeed () {
        return speed;
    }

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
