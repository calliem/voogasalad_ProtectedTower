package engine.element.sprites;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import annotations.parameter;
import engine.Updateable;


/**
 * This represents the general class for all game elements. All elements will contain methods which
 * return a boolean indicating if another type is able to target or collide with it.
 * 
 * @author Qian Wang
 * @author Greg McKeon
 *
 */

public abstract class Sprite extends GameElement implements Updateable {

    @parameter(settable = true, playerDisplay = false, defaultValue = "")
    private ImageView myImage;

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
     * This method is called when this object collides with another and should include the behavior
     * of this object, such as stopping movement, or damaging the other object.
     * 
     * @param sprite Sprite object that this object collides with
     */
    private void updateImageView () {
        myImage.setTranslateX(super.getLocationX());
        myImage.setTranslateY(super.getLocationY());
    }

    // Abstract methods

    // TODO move this to Collidable interface?
    public abstract void onCollide (Sprite sprite);

}
