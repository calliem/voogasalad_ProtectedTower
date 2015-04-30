package engine.element.sprites;

import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import annotations.parameter;


/**
 * This represents the general class for all game elements. All elements will contain methods which
 * return a boolean indicating if another type is able to target or collide with it.
 * 
 * @author Qian Wang
 * @author Greg McKeon
 *
 */

public abstract class Sprite extends GameElement {

    @parameter(settable = true, playerDisplay = false, defaultValue = "")
    private ImageView image;
    @parameter(settable = false, playerDisplay = false, defaultValue = "")
    private String imagepath;


    public Sprite () {

    }

    public void addInstanceVariables (Map<String, Object> parameters) {
        super.addInstanceVariables(parameters);
        imagepath = (String) parameters.get("Image");
        image = new ImageView(imagepath);
    }

    public ImageView getImageView () {
        return image;
    }

    // TODO remove these once testing is over
    @Deprecated
    protected void setImageView (ImageView imageview) {
        image = imageview;
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
     * This method is called when this object collides with another and should include the behavior
     * of this object, such as stopping movement, or damaging the other object.
     * 
     * @param sprite Sprite object that this object collides with
     */
    private void updateImageView () {
        image.setTranslateX(super.getLocationX());
        image.setTranslateY(super.getLocationY());
    }


    // Abstract methods

}
