package authoringEnvironment.objects;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.InstanceManager;
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

// TODO: make this into an interface
public class GameObject {

    private String myKey;
    private String myName;
    private ImageView myImageView;

    protected GameObject () {

    }

    //convert to node and then use get color for tiles
    
    public GameObject (String key, String name, ImageView image) {
        myKey = key;
        myName = name;
        myImageView = image;
    }

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
    
    public void setImageView(ImageView image){
        myImageView = image;
    }
    
    public ImageView getImageView(){
        return myImageView;
    }

    public ImageView getUniqueThumbnail () {
        ImageView uniqueNode = new ImageView(myImageView.getImage());
        return uniqueNode;
    }

    public double getWidth (){
        return myImageView.getFitWidth();
    };

    public double getHeight (){
        return myImageView.getFitHeight();
    };

    public Map<String, Object> save (){
        Map<String, Object> mapSettings = new HashMap<String, Object>();
        mapSettings.put(InstanceManager.NAME_KEY, myName);
        return mapSettings;
    };

    public Group getRoot (){
        return null;
    };

    protected String getToolTipInfo (){
        String info = "";
        info += "Name: " + myName;
        return info;
        
    }; // TODO: items to be displayed on the tooltip store
                                                 // within a properties file

}
