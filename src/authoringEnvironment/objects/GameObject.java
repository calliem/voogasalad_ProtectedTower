package authoringEnvironment.objects;

import java.util.Map;
import authoringEnvironment.AuthoringEnvironment;
import authoringEnvironment.Variables;
import javafx.scene.Node;


public abstract class GameObject {

    private String myKey;
    private String myName;
    private Node myThumbnail;

    // TODO: make all sets protected

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

    public Node getThumbnail () {
        return myThumbnail;
    };

    public void setThumbnail (Node node) {
        myThumbnail = node;
        myThumbnail.resize(AuthoringEnvironment.getEnvironmentWidth() * Variables.THUMBNAIL_SIZE_MULTIPLIER,
                           AuthoringEnvironment.getEnvironmentHeight() * Variables.THUMBNAIL_SIZE_MULTIPLIER);
        //TODO: make this save
    }

    public abstract Map<String, Object> saveToXML (); 

}
