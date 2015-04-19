package authoringEnvironment.objects;

import java.util.Map;
import authoringEnvironment.AuthoringEnvironment;
import javafx.scene.Node;

public abstract class GameObject{
    
    private String myKey;
    private String myName;
    private Node myThumbnail;
    
    //TODO: make all sets protected
    
    public String getName(){
        return myName;
    }
    
    public void setName(String name){
        myName = name;
    }    
    
    public void setKey(String key){
        myKey = key;
    }
    
    public String getKey(){
        return myKey;
    }
    
    public Node getThumbnail(){
        return myThumbnail;
    };
    
    protected void setThumbnail(Node node){
        node.resize(AuthoringEnvironment.getEnvironmentWidth()*.05,AuthoringEnvironment.getEnvironmentHeight()*.05);
        myThumbnail = node;
    }

    public abstract Map<String, Object> saveToXML(); //TODO

}
