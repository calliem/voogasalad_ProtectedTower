package engine.element.sprites;

import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import engine.Collidable;
import engine.element.GameElement;


/**
 * This represents the general class for all game elements. All elements will contain methods which
 * return a boolean indicating if another type is able to target or collide with it.
 * 
 * @author Qian Wang
 *
 */
public abstract class Sprite extends GameElement implements Collidable {

    // TODO fill in with correct string
    private static final String PARAMETER_BOUNDING_HEIGHT = "BoundingHeight";
    private static final String PARAMETER_BOUNDING_WIDTH = "BoundingWidth";

    private ImageView myImage;
    private Point2D myLocation;
    private String myType;

    public Sprite () {

    }

    // TODO remove these once testing is over
    @Deprecated
    public ImageView getImageView () {
        return myImage;
    }

    @Deprecated
    protected void setImageView (ImageView image) {
        myImage = image;
    }

    // Setters and getters

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
    public Point2D getLocation () {
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

    /**
     * Sets the type of object this is as an uppercase string
     * 
     * @param type String of the type of object
     */
    @Deprecated
    protected void setType (String type) {
        myType = type.toUpperCase();
    }

    /**
     * @return uppercase String of the type of object this is
     */
    @Deprecated
    protected String getType () {
        return myType;
    }

    public Map<String, Object> getAllParameters () {
        return super.getAllParameters();
    }

    @Override
    public double getBoundingHeight () {
        return (double) super.getParameter(PARAMETER_BOUNDING_HEIGHT);
    }

    @Override
    public double getBoundingWidth () {
        return (double) super.getParameter(PARAMETER_BOUNDING_WIDTH);
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

    /**
     * This method is called when this object collides with another and should include the behavior
     * of this object, such as stopping movement, or damaging the other object.
     * 
     * @param sprite Sprite object that this object collides with
     * @return
     */
    public abstract void collide (Sprite sprite);

}
