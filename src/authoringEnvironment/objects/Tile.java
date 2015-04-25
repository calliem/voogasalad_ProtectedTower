package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;


/**
 * Creates a visual tile object and its actions when selected or deselected
 * 
 * @author Callie Mao
 *
 */
public class Tile extends Rectangle {

    
    //TODO: maybe make this a game object?
    
    private ArrayList<String> myTags;
    private Color myColor;
    private String myKey;
    private String myName;

    private static final String COLOR = "Color";
    private static final String TAGS = "Tag";
    
    //TODO: when have tag object, just store key and then make it work here 

    // will have the same image for a path?
    // TODO: create a text box to set grid size and a slider to set tile size
    // that would only allow numbers in correct increments that would fit
    private static final Color DEFAULT_COLOR = Color.TRANSPARENT;

    public Tile () {
        new Rectangle();
        myColor = DEFAULT_COLOR;
        setFill(DEFAULT_COLOR);
        setOpacity(0.4);
        myName = null;
        myTags = new ArrayList<String>();
    }
    
    /**
     * Constructor for reconstructing a tile from one already saved into the controller.
     * @param c The controller that holds all saved data
     * @param key The tile's key that is mapped to parameters within the controller
     */
    public Tile(Controller c, String key){
        Map<String, Object> params = c.getPartCopy(key);
        myName = (String) params.get(InstanceManager.NAME_KEY);
        myTags = (ArrayList<String>) params.get(Variables.PARAMETER_TAGS);
        myColor = (Color) params.get(Variables.PARAMETER_COLOR);
        setFill(myColor);
    }

    public void positionTile (int tileSize, int i, int j) {
        setTranslateX(j * tileSize);
        setTranslateY(i * tileSize);
    }

    public void setTileSize (double size, int rowNum, int colNum) {
        setWidth(size);
        setHeight(size);
        setTranslateX(colNum * size);
        setTranslateY(rowNum * size);
    }

    public ArrayList<String> getTags () {
        return myTags;
    }

    public Color getColor () {
        return myColor;
    }
    
    public void setFill(Color color){
        super.setFill(color);
        myColor = color;
    }
    
    public String getKey(){
        return myKey;
    }
    
    public void setKey(String key){
        myKey = key;
    }

    public Map<String, Object> save () {
        Map<String, Object> mapSettings = new HashMap<String, Object>();
        mapSettings.put(InstanceManager.NAME_KEY, myName);
        mapSettings.put(TAGS, myTags);
        mapSettings.put(COLOR, myColor);
        return mapSettings;
    }

}
