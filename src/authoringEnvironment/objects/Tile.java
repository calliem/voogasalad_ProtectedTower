package authoringEnvironment.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.Variables;


/**
 * Creates a visual tile object and its actions when selected or deselected
 * 
 * @author Callie Mao
 *
 */
public class Tile extends Rectangle {

    private List<String> myTags;
    private Color myColor;
    private String myKey;
    private String myName;

    private static final String COLOR = "Color";
    private static final String TAGS = "Tag";

    private static final Color DEFAULT_COLOR = Color.TRANSPARENT;
    private static final double DEFAULT_OPACITY = 0.4;
    private static final String TAGS_HEADER = "\nTags: ";

    public Tile () {
        myColor = DEFAULT_COLOR;
        setFill(DEFAULT_COLOR);
        setOpacity(DEFAULT_OPACITY);
        myName = null;
        myTags = new ArrayList<String>();
        Tooltip t = new Tooltip(getToolTipInfo());
        t.setTextAlignment(TextAlignment.LEFT);
        Tooltip.install(this, t);
    }
    
    public void positionTile (int tileSize, int i, int j) {
        setTranslateX(j * tileSize);
        setTranslateY(i * tileSize);
    }

    public String getToolTipInfo () {
        String info = Variables.EMPTY_STRING;
        info += Variables.NAME_HEADER + myName;
        if (myTags.size() > 0) {
            info += TAGS_HEADER;
            for (String tag : myTags) {
                info += Variables.COMMA_DELIMITER + tag;
            }
        }
        return info;
    }

    public void setTileSize (double size, int rowNum, int colNum) {
        setWidth(size);
        setHeight(size);
        setTranslateX(colNum * size);
        setTranslateY(rowNum * size);
    }

    public List<String> getTags () {
        return myTags;
    }

    public Color getColor () {
        return myColor;
    }
    
    public void setFill (Color color) {
        super.setFill(color);
        myColor = color;
    }

    public String getKey () {
        return myKey;
    }

    public void setKey (String key) {
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
