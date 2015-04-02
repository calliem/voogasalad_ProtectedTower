package authoring.environment;

import java.util.ArrayList;
import javafx.scene.Group;
import authoring.environment.objects.PathView;
import authoring.environment.objects.SpriteView;


public class MapEditor extends MainEditor {

    public SpriteView[][] getTiles(){
        // TODO return actual map tiles
        return new SpriteView[0][0];
    }

    public ArrayList<PathView> getPaths(){ //to be accessed by Levels
        // TODO return actual paths
        return new ArrayList<>();
    }

    public ArrayList<SpriteView> getMaps(){
        // TODO return actual GameMaps
        return new ArrayList<>();
    };

    @Override
    protected Group configureUI() {
        // TODO Auto-generated method stub
        return null;
    }

}
