package authoringEnvironment.objects;

import javafx.scene.image.ImageView;

public class PreviewObject extends GameObject{
    
    
    public PreviewObject(String key, String name, ImageView thumbnail){
        super();
        setKey(key);
        setName(name);
        setThumbnail(thumbnail);
    }

}
