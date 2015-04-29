package authoringEnvironment.editors;

import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import authoringEnvironment.Controller;
import authoringEnvironment.InstanceManager;
import authoringEnvironment.objects.TileView;


/**
 * This class extends SpriteEditor and allows the user to create a tile object. Because of slow
 * runtimes from large image sizes, users are currently restricted to only being able to utilize
 * rectanglular tiles with customized colors. If applicable, the tiles can be given tags that can
 * later be utilized in the MapEditor to set specific terrain tags, and the tags can later be
 * matched by the user when placing towers.
 * 
 * @author Kevin He
 *
 */
public class TileEditor extends SpriteEditor {
    private Group visuals;
    private TagDisplay tags;
    private static final Color BACKGROUND_COLOR = Color.GRAY;
    private static final int PADDING = 10;
    
    public TileEditor (Controller c, String name) {
        super(c, name);
        prompt.setImageChooser(false);
        prompt.setColorPicker(true);
    }
    
    @Override
    protected void checkNeededParts(){
        
    }
    
    @Override
    protected void addPart(){
        createTile(prompt.getEnteredName(), prompt.getColorChosen());
    }
    
    private void createTile(String name, Color color){
        TileView tile = new TileView(myController, name, color);
        tile.initiateEditableState();
        updateOnExists(tile);
        
        Map<String, Object> part = tile.getTileInfo();
        part.put(InstanceManager.PART_TYPE_KEY, partNames.getString(editorType));
        try{
            myController.addPartToGame(part);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
