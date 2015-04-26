package engine.element.sprites;

import javafx.geometry.Point2D;
import engine.Collidable;
import annotations.parameter;


/**
 * This class represents all game elements that have a certain bounding height and width that is
 * used to determine collisions.
 * 
 * @author Qian Wang
 *
 */
public class GameElement implements Collidable {

    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private double boundingHeight;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private double boundingWidth;

    private Point2D myLocation;

    @Override
    public double getBoundingHeight () {
        return boundingHeight;
    }

    // protected void setBoundingHeight (double boundingHeight) {
    // this.boundingHeight = boundingHeight;
    // }

    @Override
    public double getBoundingWidth () {
        return boundingWidth;
    }

    // protected void setBoundingWidth (double boundingWidth) {
    // this.boundingWidth = boundingWidth;
    // }

    /**
     * Sets the location of the sprite
     * 
     * @param location Point2D object representing (x, y) coordinates
     */
    public void setLocation (Point2D location) {
        myLocation = location;
    }

    /**
     * Sets the location of the sprite
     * 
     * @param x double of x-coordinate
     * @param y double of y-coordinate
     */
    public void setLocation (double x, double y) {
        myLocation = new Point2D(x, y);
    }

    /**
     * @return Point2D representing coordinate location of object
     */
    protected Point2D getLocation () {
        return new Point2D(myLocation.getX(), myLocation.getY());
    }

    @Override
    public double getLocationX () {
        return myLocation.getX();
    }

    @Override
    public double getLocationY () {
        return myLocation.getY();
    }
}
