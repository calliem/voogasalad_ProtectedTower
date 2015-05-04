package authoringEnvironment.objects;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import authoringEnvironment.InstanceManager;

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

public class GameObject extends SimpleObject {

    public GameObject (String key, String name, ImageView image) {
        super(key, name, image);
    }
    
    protected GameObject(){};
    
    public void setKey (String key) {
        super.setKey(key);
    }

    public Map<String, Object> save () {
        Map<String, Object> mapSettings = new HashMap<String, Object>();
        mapSettings.put(InstanceManager.NAME_KEY, getName());
        return mapSettings;
    }

    public Group getRoot (){
        return new Group();
    };

    protected String getToolTipInfo () {
        String info = "";
        info += "Name: " + getName();
        return info;

    }

    public void setName (String name) {
        super.setName(name);
    }
    
    public void setImageView(ImageView image){
        super.setImageView(image);
    }

}