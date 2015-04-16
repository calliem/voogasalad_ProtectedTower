package authoringEnvironment.objects;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Creates a visual tile object and its actions when selected or deselected
 * 
 * @author Callie Mao
 *
 */
public class Tile extends Rectangle {

    // private ImageView myImage
    // private double myTileSize; // TODO: this should be written in the map since the tile does not
    // need to kno this
    private int myColNum;
    private int myRowNum;
    private ArrayList<String> myTags;
    private Color myColor;
    private boolean isSelected;

    // will have the same image for a path?
    // TODO: create a text box to set grid size and a slider to set tile size
    // that would only allow numbers in correct increments that would fit
    private static final Color DEFAULT_COLOR = Color.TRANSPARENT;

    public Tile () {
        // TODO: fix tile to make it more general and not have col nums and x/y generated here
        // TODO: store part keys in the xml file for tilemap
        setFill(DEFAULT_COLOR);
        setOpacity(0.4);
        isSelected = false;
    }

    public void positionTile (int tileSize, int i, int j) {
        setTranslateX(j * tileSize);
        setTranslateY(i * tileSize);
    }

    public void setTileSize (int tileSize) {
        setWidth(tileSize);
        setHeight(tileSize);
    }

    public void addTag (String tag) {
        myTags.add(tag);
    }

    // should only be able to remove already existing tags
    public void removeTag (String tag) {
        myTags.remove(tag);
    }

    /*
     * public void setImage(ImageView image) { myImage = image; }
     */

    public void setTileSize (double size) {
        setWidth(size);
        setHeight(size);
        setTranslateX(myColNum * size);
        setTranslateY(myRowNum * size);
    }

    // selection stuff is all for pathing. Need separate methods for updating the tile
    // active refers to if it is selected as part of a path
    public void select () {
        if (!isSelected) {
            setOpacity(0.2); // change image entirely
        }
        else {
            setOpacity(1);
        }
        isSelected = !isSelected;
    }

    public boolean isSelected () {
        return isSelected;
    }

}
