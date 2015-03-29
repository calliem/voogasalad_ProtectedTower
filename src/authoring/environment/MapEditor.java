package authoring.environment;

import java.util.ArrayList;
import javafx.scene.Group;
import authoring.environment.objects.Path;
import authoring.environment.objects.Sprite;


public class MapEditor extends MainEditor {

    public Sprite[][] getTiles(){
        // TODO return actual map tiles
        return new Sprite[0][0];
    }

    public ArrayList<Path> getPaths(){ //to be accessed by Levels
        // TODO return actual paths
        return new ArrayList<>();
    }

    public ArrayList<Sprite> getMaps(){
        // TODO return actual GameMaps
        return new ArrayList<>();
    };

   /* @Override
    protected Group configureUI() {
        // TODO Auto-generated method stub
        return null;
    }
*/
}
