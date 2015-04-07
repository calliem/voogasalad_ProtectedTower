package engine.element.sprites;

import javafx.geometry.Point2D;
import engine.InsufficientParametersException;


/**
 * This class represents game elements that move around the game space. Required abstract methods
 * include those to control movement. A variable representing velocity can be used to move.
 * 
 * @author Qian Wang
 *
 */
public abstract class MoveableSprite extends Sprite {
    private Point2D myVelocity;
    private double myRange;

    public MoveableSprite () throws InsufficientParametersException {
        super();
        // TODO Auto-generated constructor stub
    }

    // Setters and getters

    /**
     * Sets the velocity of the sprite to a normalized value
     * 
     * @param velocity Point2D object representing x and y components of velocity
     */
    protected void setLocation (Point2D velocity) {
        myVelocity = velocity.normalize();
    }

    /**
     * Sets the velocity of the sprite to a normalized value
     * 
     * @param x double of x component of velocity
     * @param y double of y component of velocity
     */
    protected void setLocation (double x, double y) {
        myVelocity = new Point2D(x, y).normalize();
    }

    /**
     * Sets the range of the sprite movement. This value is used to test is the sprite has traveled
     * past its rated range, in which case it can stop moving.
     * 
     * @param range double value of the range the sprite should move before stopping
     */
    protected void setRange (double range) {
        myRange = range;
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
     * This method is called when this object collides with another and should include the behavior
     * of this object, such as stopping movement, or damaging the other object.
     * 
     * @param sprite Sprite object that this object collides with
     * @return
     */
    public abstract void collide (Sprite sprite);

    /**
     * This method is called by the game loop to update the view of the MovableSprite object. This
     * method should be used to define how this object moves and changes coordinates on the screen.
     */
    public abstract void move ();

}
