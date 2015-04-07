package engine.sprites;

import java.util.Map;
import javafx.geometry.Point2D;
import engine.GameElement;
import engine.InsufficientParametersException;


/**
 * This represents the general class for all game elements. All elements will contain methods which
 * return a boolean indicating if another type is able to target or collide with it.
 * 
 * @author Qian Wang
 *
 */
public abstract class Sprite extends GameElement {

    private Point2D myLocation;
    private String myType;

    public Sprite () throws InsufficientParametersException {

    }

    // public abstract List<String> getParameters ();

    // public double getSize () {
    // return 0;
    // }

    // Setters and getters

    /**
     * Sets the location of the sprite
     * 
     * @param location Point2D object representing (x, y) coordinates
     */
    protected void setLocation (Point2D location) {
        myLocation = location;
    }

    /**
     * Sets the location of the sprite
     * 
     * @param x double of x-coordinate
     * @param y double of y-coordinate
     */
    protected void setLocation (double x, double y) {
        myLocation = new Point2D(x, y);
    }

    /**
     * Sets the type of object this is as an uppercase string
     * 
     * @param type String of the type of object
     */
    protected void setType (String type) {
        myType = type.toUpperCase();
    }

    /**
     * @return uppercase String of the type of object this is
     */
    protected String getType () {
        return myType;
    }

    // Abstract methods

    /**
     * Finds if a certain type of object can target this object
     * 
     * @param type String
     * @return true if the given type can target this object's type
     */
    public abstract boolean isTargetableBy (String type);

    /**
     * Finds if a certain type of object can collide with this object
     * 
     * @param type String
     * @return true if the given type can collide with this object's type
     */
    public abstract boolean isCollidableWith (String type);

}
