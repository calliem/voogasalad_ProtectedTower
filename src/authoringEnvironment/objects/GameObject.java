package authoringEnvironment.objects;

import java.util.Map;
import javafx.scene.image.ImageView;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Variables;


/**
 * General abstraction for all game objects that do not get their properties directly from parameter
 * properties files (because they are not simply integer or string parameters that can be entered
 * in). This class contains the basic properties and methods that each game object in the game
 * should have. These objects are directly created by the user before being saved as opposed to
 * being created after parameters are specified. While game objects populated from sprite settings
 * will also need similar methods, those spriteviews have different functionalities and are created
 * differently.
 * 
 * @author Callie Mao
 *
 */

public abstract class GameObject {

    private String myKey;
    private String myName;
    private ImageView myThumbnail;

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
    }

    public void setKey (String key) {
        myKey = key;
    }

    public String getKey () {
        return myKey;
    }

    public ImageView getThumbnail () {
        return myThumbnail;
    }

    public ImageView getUniqueThumbnail () {
        ImageView uniqueNode = new ImageView(myThumbnail.getImage());
        return uniqueNode;
    }

    // TODO: spriteviews do not have thumbnails....they simply have images. Create thumbnails for
    // them or ensure that an if statement is added to updateable display to check if thumbnails are
    // stored in the hasmap or not. if not then get the image
    // TODO: jk they do need thumbnails. JavaFX won't let you have two of the same node (can't have
    // both a small and a large one - you have to make the small one directly)
    // TODO: use the stored imagepath in the hashmap for normal spriteviews and create one normal
    // large image and also one normal small thumbnail

    /**
     * Creates a thumbnail from a filepath. This method is ideal for duplicating and resizing an
     * already existing ImageView (as this
     * allows for cloning an image which can sidestep JavaFX's node limitations)
     * 
     * @param imagePath
     */
    /*
     * public void setThumbnail (String imagePath) {
     * 
     * ImageView thumbnail = new ImageView(new Image(imagePath));
     * 
     * thumbnail.setFitWidth(AuthoringEnvironment.getEnvironmentWidth() *
     * Variables.THUMBNAIL_SIZE_MULTIPLIER);
     * thumbnail.setFitHeight(AuthoringEnvironment.getEnvironmentHeight() *
     * Variables.THUMBNAIL_SIZE_MULTIPLIER);
     * myThumbnail = thumbnail;
     * 
     * // setting thm
     * /*
     * myImage.resize(AuthoringEnvironment.getEnvironmentWidth() *
     * Variables.THUMBNAIL_SIZE_MULTIPLIER,
     * AuthoringEnvironment.getEnvironmentHeight() *
     * Variables.THUMBNAIL_SIZE_MULTIPLIER);
     * 
     * }
     */

    /**
     * Sets the thumbnail to be the image resized to the given standards. This can only be used on
     * images that are not utilized/displayed elsewhere in the program, since JavaFX only allows for
     * one node to exist at once
     * 
     * @param image
     */
    public void setThumbnail (ImageView image) {
        ImageView thumbnail = new ImageView(image.getImage());
        thumbnail.setFitWidth(AuthoringEnvironment.getEnvironmentWidth() *
                              Variables.THUMBNAIL_SIZE_MULTIPLIER);
        thumbnail.setFitHeight(AuthoringEnvironment.getEnvironmentHeight() *
                               Variables.THUMBNAIL_SIZE_MULTIPLIER);
        myThumbnail = thumbnail;
    }

    public abstract Map<String, Object> saveToXML ();

}
