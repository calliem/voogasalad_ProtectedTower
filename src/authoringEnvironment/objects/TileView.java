package authoringEnvironment.objects;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;

public class TileView extends ObjectView{
    private String myName;
    private Rectangle tile;
    private Color tileColor;
    private static final int TILE_SIZE = 100;
    private static final String COLOR_KEY = "Color";
    
    public TileView (Controller controller, String name, Color color) {
        super(controller);
        myName = name;
        tileColor = color;
        tile = new Rectangle(TILE_SIZE, TILE_SIZE, tileColor);
        
        objectLayout.getChildren().addAll(tile, tagGroup);
    }
    
    public Map<String, Object> getTileInfo(){
        Map<String, Object> info = new HashMap<>();
        info.put(InstanceManager.NAME_KEY, myName);
        info.put(COLOR_KEY, tileColor);
        return info;
    }
}
