package engine.element.sprites;

import annotations.parameter;


/**
 * This class represents all game elements that have a certain bounding height and width that is
 * used to determine collisions.
 * 
 * @author Qian Wang
 *
 */
public class GameElement {

    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private double boundingHeight;
    @parameter(settable = true, playerDisplay = true, defaultValue = "0.0")
    private double boundingWidth;

    public double getBoundingHeight () {
        return boundingHeight;
    }

    // protected void setBoundingHeight (double boundingHeight) {
    // this.boundingHeight = boundingHeight;
    // }

    public double getBoundingWidth () {
        return boundingWidth;
    }

//    protected void setBoundingWidth (double boundingWidth) {
//        this.boundingWidth = boundingWidth;
//    }

}
