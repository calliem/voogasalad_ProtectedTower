package engine.element.sprites;

import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import annotations.parameter;
import engine.Updateable;


/**
 * This represents the general class for all game elements. All elements will contain methods which
 * return a boolean indicating if another type is able to target or collide with it.
 * 
 * @author Qian Wang
 *
 */

public abstract class Sprite extends GameElement implements Updateable {

    @parameter(settable = true, playerDisplay = false, defaultValue = "")
    private ImageView myImage;
    @parameter(settable = true, playerDisplay = true, defaultValue = "Unnamed")
    private String myName;
    @parameter(settable = true, playerDisplay = true, defaultValue = "Basic")
    private String myType;

    public Sprite () {

    }

    public ImageView getImageView () {
        return myImage;
    }

    // TODO remove these once testing is over
    @Deprecated
    protected void setImageView (ImageView image) {
        myImage = image;
    }

    // Setters and getters

    @Override
    public void setLocation (Point2D location) {
        super.setLocation(location);
        updateImageView();
    }

    @Override
    public void setLocation (double x, double y) {
        super.setLocation(x, y);
        updateImageView();
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

    @Deprecated
    public Map<String, Object> getAllParameters () {
        return null; // super.getAllParameters();
    }

    // Abstract methods

    /**
     * This method is called when this object collides with another and should include the behavior
     * of this object, such as stopping movement, or damaging the other object.
     * 
     * @param sprite Sprite object that this object collides with
     */
    private void updateImageView () {
        myImage.setTranslateX(super.getLocationX());
        myImage.setTranslateY(super.getLocationY());
    }

    public abstract void onCollide (Sprite sprite);

}
