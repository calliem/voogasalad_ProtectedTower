package authoringEnvironment.objects;

import javafx.scene.Node;

public abstract class GameObject extends Node{
    
    public abstract String getName();
    public abstract Node getThumbnail();
}
