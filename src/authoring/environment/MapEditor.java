package authoring.environment;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import authoring.environment.objects.GameMap;
import authoring.environment.objects.Path;
import authoring.environment.objects.Tile;


public class MapEditor extends MainEditor {

    public Tile[][] getTiles(){
        // TODO return actual map tiles
        return new Tile[0][0];
    }

    public ArrayList<Path> getPaths(){ //to be accessed by Levels
        // TODO return actual paths
        return new ArrayList<>();
    }

    public ArrayList<GameMap> getMaps(){
        // TODO return actual GameMaps
        return new ArrayList<>();
    };

    @Override
    protected GridPane configureUI() {
        // TODO Auto-generated method stub
        return null;
    }

}
