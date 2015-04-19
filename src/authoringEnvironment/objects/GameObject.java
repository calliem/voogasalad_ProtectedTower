package authoringEnvironment.objects;

import javafx.scene.Node;

public abstract class GameObject extends Node{
    
    private int myKey;
    private String myName;
    
    public String getName(){
        return myName;
    }
    
    public void setName(String name){
        myName = name;
    }    
    
    public void setKey(int key){
        myKey = key;
    }
    
    public int getKey(){
        return myKey;
    }
    
    public abstract Node getThumbnail();

}
