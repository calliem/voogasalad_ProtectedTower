    package authoringEnvironment.objects;

import java.util.Map;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
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
    private ImageView myImageView;

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
    
    public abstract int getWidth();
    
    public abstract int getHeight();

    public ImageView getImagePreview () {
        return myImageView;
    }

    public ImageView getUniqueThumbnail () {
        ImageView uniqueNode = new ImageView(myImageView.getImage());

        Tooltip t = new Tooltip(getToolTipInfo());
        t.setTextAlignment(TextAlignment.LEFT);
        Tooltip.install(uniqueNode, t);
        
        return uniqueNode;
    }

    /**
     * Sets the thumbnail to be the image resized to the given standards. This can only be used on
     * images that are not utilized/displayed elsewhere in the program, since JavaFX only allows for
     * one node to exist at once
     * 
     * @param image
     */
    public void setImagePreview (ImageView image) {
        
        //TODO: don't set the thumbnail size here, set it in the updatablesdisplay
        ImageView thumbnail = new ImageView(image.getImage());
        //thumbnail.setFitWidth(AuthoringEnvironment.getEnvironmentWidth() *
          //                    Variables.THUMBNAIL_SIZE_MULTIPLIER);
        //thumbnail.setFitHeight(AuthoringEnvironment.getEnvironmentHeight() *
         //                      Variables.THUMBNAIL_SIZE_MULTIPLIER);
        myImageView = thumbnail;
    }

    public abstract Map<String, Object> save ();
    
    public abstract Group getRoot();
    
    protected abstract String getToolTipInfo(); //TODO: items to be displayed on the tooltip store within a properties file

}
