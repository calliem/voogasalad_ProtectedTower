package authoringEnvironment.objects;

import java.util.Map;
import javafx.scene.Node;

public abstract class GameObject{
    
    private String myKey;
    private String myName;
    
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
    
    public abstract Node getThumbnail();

    public abstract Map<String, Object> saveToXML(); //TODO

}
