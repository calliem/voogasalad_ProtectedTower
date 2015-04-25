package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import authoringEnvironment.InstanceManager;


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
        // TODO: fix tile to make it more general and not have col nums and x/y generated here
        // TODO: store part keys in the xml file for tilemap
        new Rectangle();
        myColor = DEFAULT_COLOR;
        setFill(DEFAULT_COLOR);
        setOpacity(0.4);
        myName = null;
        myTags = new ArrayList<String>();
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

    public Map<String, Object> saveToXML () {
        Map<String, Object> mapSettings = new HashMap<String, Object>();
        mapSettings.put(InstanceManager.NAME_KEY, myName);
        mapSettings.put(TAGS, myTags);
        mapSettings.put(COLOR, myColor);
        return mapSettings;
    }

}
