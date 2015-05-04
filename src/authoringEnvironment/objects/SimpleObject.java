//
//

package authoringEnvironment.objects;

import javafx.scene.image.ImageView;


/**
 * This class constructs a simple object consisting solely of a name, key, and image. These objects
 * are primarily to be displayed within imageviews, encapsulate data, and reduce the amount of
 * information passed around.
 * 
 * @author Callie Mao
 *
 */
public class SimpleObject {

    private String myKey;
    private String myName;
    private ImageView myImageView;

    protected SimpleObject () {
    }

    public SimpleObject (String key, String name, ImageView image) {
        myKey = key;
        myName = name;
        myImageView = image;
    }

    public String getName () {
        return myName;
    }

    public String getKey () {
        return myKey;
    }

    public ImageView getImageView () {
        return myImageView;
    }

    public ImageView getUniqueThumbnail () {
        ImageView uniqueNode = new ImageView(myImageView.getImage());
        return uniqueNode;
    }

    public double getWidth () {
        return myImageView.getFitWidth();
    }

    public double getHeight () {
        return myImageView.getFitHeight();
    }

    // below protected methods are primarily for subclasses

    protected void setKey (String key) {
        myKey = key;
    }

    protected void setImageView (ImageView image) {
        myImageView = image;
    }

    protected void setName (String name) {
        myName = name;
    }

}
