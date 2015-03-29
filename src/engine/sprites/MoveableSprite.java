package engine.sprites;

import javafx.geometry.Point2D;
import engine.InsufficientParametersException;


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
    public abstract void target (Sprite s);

    public abstract boolean collide (Sprite s);

    public abstract void move ();

}
