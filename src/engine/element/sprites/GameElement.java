package engine.element.sprites;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import annotations.parameter;
import engine.Collidable;
import engine.Reflectable;


/**
 * This class represents all game elements that have a certain bounding height and width that is
 * used to determine collisions.
 * 
 * @author Qian Wang
 * @author Greg McKeon
 *
 */
public abstract class GameElement implements Collidable, Reflectable {

    @parameter(settable = true, playerDisplay = true, defaultValue = "Unnamed")
    private String name;
    @parameter(settable = false, playerDisplay = true, defaultValue = "Basic")
    private List<String> tags;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double boundingHeight;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private Double boundingWidth;
    /**
     * Holds the current location of the object
     */
    @parameter(settable = false, playerDisplay = true)
    private Point2D myLocation;

    public final static String ALIVE_STATE = "alive";
    public final static String DEAD_STATE = "dead";
    public final static String[] possibleStates = { ALIVE_STATE, DEAD_STATE };
    @parameter(settable = false, playerDisplay = false)
    private String stateTag = ALIVE_STATE;

    public GameElement () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        name = (String) parameters.get("name");
        tags = (List<String>) parameters.get("tags");
        boundingHeight = (Double) parameters.get("boundingHeight");
        boundingWidth = (Double) parameters.get("boundingWidth");
    }

    // Getters and setters

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

    protected String getName () {
        return name;
    }

    public String getState () {
        return stateTag;
    }

    public void setState (String state) {
        if (Arrays.asList(possibleStates).contains(state))
            stateTag = state;
    }

    public void setDead () {
        stateTag = DEAD_STATE;
    }

    /**
     * @return List<String> of the tags associated with a game element
     */
    public List<String> getTags () {
        return Collections.unmodifiableList(tags);
    }

    /**
     * @return String of all tags associated with an object separated by a space
     */
    public String getTagsToString () {
        return this.getTagsToString(" ");
    }

    /**
     * Returns a formatted list of tags
     * 
     * @param sep String separator to put in between tags
     * @return String of all tags associated with an object separated by a given String
     */
    public String getTagsToString (String sep) {
        if (tags.size() == 0) { return ""; }
        StringBuilder str = new StringBuilder();
        str.append(tags.get(0));
        for (int i = 1; i < tags.size(); i++) {
            str.append(sep);
            str.append(tags.get(i));
        }
        return str.toString();
    }

    // Methods to implement Collidable interface

    @Override
    public double getBoundingHeight () {
        return boundingHeight;
    }

    @Override
    public double getBoundingWidth () {
        return boundingWidth;
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
